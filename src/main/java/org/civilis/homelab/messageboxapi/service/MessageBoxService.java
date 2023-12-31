package org.civilis.homelab.messageboxapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.civilis.homelab.messageboxapi.exception.NotFoundException;
import org.civilis.homelab.messageboxapi.mapping.HeaderMapper;
import org.civilis.homelab.messageboxapi.mapping.MessageMapper;
import org.civilis.homelab.messageboxapi.mapping.NotificationMapper;
import org.civilis.homelab.messageboxapi.model.Header;
import org.civilis.homelab.messageboxapi.model.Message;
import org.civilis.homelab.messageboxapi.model.Notification;
import org.civilis.homelab.messageboxapi.model.search.*;
import org.civilis.homelab.messageboxapi.persistence.entity.HeaderEntity;
import org.civilis.homelab.messageboxapi.persistence.entity.MessageEntity;
import org.civilis.homelab.messageboxapi.persistence.entity.NotificationEntity;
import org.civilis.homelab.messageboxapi.persistence.repository.HeaderRepository;
import org.civilis.homelab.messageboxapi.persistence.repository.MessageRepository;
import org.civilis.homelab.messageboxapi.persistence.repository.NotificationRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageBoxService {

    private final NotificationMapper notificationMapper = NotificationMapper.MAPPER;
    private final HeaderMapper headerMapper = HeaderMapper.MAPPER;
    private final MessageMapper messageMapper = MessageMapper.MAPPER;

    private final NotificationRepository notificationRepository;
    private final HeaderRepository headerRepository;
    private final MessageRepository messageRepository;

    private final SearchUtil searchUtil;

    public PageResponse<Header> getHeadersByUsername(String username, String status, Integer page, Integer pageSize) {
        FilterPageRequest<Header> filterPageRequest = buildHeaderFilterRequest(username, status, page, pageSize);
        return getHeaderPage(filterPageRequest);
    }

    private FilterPageRequest<Header> buildHeaderFilterRequest(String username, String status, Integer page, Integer pageSize) {
        FilterPageRequest<Header> filterPageRequest = new FilterPageRequest<>();
        filterPageRequest.setPage(page);
        filterPageRequest.setPageSize(pageSize);
        Header filter = new Header();
        filter.setUsername(username);
        filter.setStatus(status);
        filterPageRequest.setFilter(filter);
        SortField sortField = new SortField("dateTime", Sort.Direction.ASC);
        LinkedHashSet<SortField> sortFields = new LinkedHashSet<>();
        sortFields.add(sortField);
        filterPageRequest.setSortFields(sortFields);
        return filterPageRequest;
    }

    public MessagePageResponse getMessagesByHeaderId(Long headerId, boolean archive, Integer page, Integer pageSize) {
        HeaderEntity headerEntity = headerRepository.findById(headerId)
                .orElseThrow(() -> new NotFoundException("header not found for id: " + headerId));
        Header header = headerMapper.convertEntityToJsonModel(headerEntity);
        FilterPageRequest<Message> filterPageRequest = buildMessageFilterRequest(headerId, archive, page, pageSize);
        PageResponse<Message> pageResponse = getMessagePage(filterPageRequest);
        MessagePageResponse response = new MessagePageResponse(header, pageResponse);
        return response;
    }

    private FilterPageRequest<Message> buildMessageFilterRequest(Long headerId, boolean archive, Integer page, Integer pageSize) {
        FilterPageRequest<Message> filterPageRequest = new FilterPageRequest<>();
        filterPageRequest.setPage(page);
        filterPageRequest.setPageSize(pageSize);
        Message filter = new Message();
        filter.setHeaderId(headerId);
        filter.setArchive(archive);
        filterPageRequest.setFilter(filter);
        SortField sortField = new SortField("dateTime", Sort.Direction.ASC);
        LinkedHashSet<SortField> sortFields = new LinkedHashSet<>();
        sortFields.add(sortField);
        filterPageRequest.setSortFields(sortFields);
        return filterPageRequest;
    }

    public PageResponse<Message> getMessagePage(FilterPageRequest<Message> request) {
        Message filter = request.getFilter() == null ? new Message() : request.getFilter();
        Page<MessageEntity> page = messageRepository.findAll(
                Example.of(
                        messageMapper.convertSanitizedJsonModelToEntity(filter),
                        buildMessageMatcher(filter)
                )
                , searchUtil.getPageRequest(request)
        );

        PageResponse<Message> response = new PageResponse<>();
        searchUtil.setPageResponseFields(page, response);
        response.setContent(page.get().map(messageMapper::convertEntityToJsonModel).toList());
        return response;
    }

    public PageResponse<Header> getHeaderPage(FilterPageRequest<Header> request) {
        Header filter = request.getFilter() == null ? new Header() : request.getFilter();
        Page<HeaderEntity> page = headerRepository.findAll(
                Example.of(
                        headerMapper.convertSanitizedJsonModelToEntity(filter),
                        buildHeaderMatcher(filter)
                )
                , searchUtil.getPageRequest(request)
        );

        PageResponse<Header> response = new PageResponse<>();
        searchUtil.setPageResponseFields(page, response);
        response.setContent(page.get().map(headerMapper::convertEntityToJsonModel).toList());
        return response;
    }

    private ExampleMatcher buildHeaderMatcher(Header filter) {
        return ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withMatcher("username", searchUtil.getPropertyMatcher(filter.getUsername()));
    }

    private ExampleMatcher buildMessageMatcher(Message filter) {
        return ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withMatcher("kind", searchUtil.getPropertyMatcher(filter.getKind()))
                .withMatcher("sender", searchUtil.getPropertyMatcher(filter.getSender()))
                .withMatcher("recipient", searchUtil.getPropertyMatcher(filter.getRecipient()))
                .withMatcher("status", searchUtil.getPropertyMatcher(filter.getStatus()));
    }

    @Transactional
    public Notification processNotification(Notification notification) {
        HeaderEntity headerEntity = findHeaderByProductIdAndUsername(notification);
        headerEntity = headerRepository.save(headerEntity);

        NotificationEntity notificationEntity = notificationMapper.convertJsonModelToEntity(notification);
        notificationEntity = notificationRepository.save(notificationEntity);

        MessageEntity messageEntity = createNewMessageEntity(notification, headerEntity.getId(), notificationEntity.getId());
        messageRepository.save(messageEntity);

        return notificationMapper.convertEntityToJsonModel(notificationRepository.save(notificationEntity));
    }

    private HeaderEntity findHeaderByProductIdAndUsername(Notification notification) {
        String username = getUsernameFromNotification(notification);
        HeaderEntity headerEntity = headerRepository.findByProductIdAndUsername(notification.getProductId(), username)
                .orElse(createNewHeaderEntity(notification, username));

        // update datetime & status
        headerEntity.setStatus("NEW");
        headerEntity.setDateTime(notification.getDateTime());

        return headerEntity;
    }

    private HeaderEntity createNewHeaderEntity(Notification notification, String username) {
        HeaderEntity headerEntity = new HeaderEntity();
        headerEntity.setDateTime(notification.getDateTime());
        headerEntity.setProductId(notification.getProductId());
        headerEntity.setUsername(username);
        headerEntity.setStatus("NEW");
        return headerEntity;
    }

    private MessageEntity createNewMessageEntity(Notification notification, Long headerId, Long notificationId) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setHeaderId(headerId);
        messageEntity.setNotificationId(notificationId);
        messageEntity.setKind(notification.getKind());
        messageEntity.setSender(notification.getSender());
        messageEntity.setRecipient(notification.getRecipient());
        messageEntity.setContent(notification.getContent());
        messageEntity.setDocumentId(notification.getDocumentId());
        messageEntity.setDateTime(notification.getDateTime());
        messageEntity.setStatus("NEW");
        messageEntity.setArchive(false);
        return messageEntity;
    }

    private String getUsernameFromNotification(Notification notification) {
        if (!"BOIP".equalsIgnoreCase(notification.getRecipient().trim())) {
            return notification.getRecipient();
        }
        return notification.getSender();
    }

}
