package org.civilis.homelab.messageboxapi.model.search;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchUtil {

    public ExampleMatcher.GenericPropertyMatcher getPropertyMatcher(String searchValue) {
        if (searchValue == null)
            return ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching();

        if (searchValue.startsWith("%")) {
            if (searchValue.endsWith("%")) {
                return ExampleMatcher.GenericPropertyMatchers.contains();
            } else {
                return ExampleMatcher.GenericPropertyMatchers.endsWith();
            }
        } else if (searchValue.endsWith("%")) {
            return ExampleMatcher.GenericPropertyMatchers.startsWith();
        } else {
            return ExampleMatcher.GenericPropertyMatchers.exact();
        }
    }

    public PageRequest getPageRequest(FilterPageRequest<?> request) {
        return PageRequest.of(request.getPage() - 1, request.getPageSize(), buildSort(request));
    }

    public void setPageResponseFields(Page<?> page, PageResponse<?> response) {
        response.setTotalElements((int) page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setPageNumber(page.getNumber() + 1);
        response.setPageSize(page.getSize());
    }

    private Sort buildSort(FilterPageRequest<?> request) {
        if (request.getSortFields() == null || request.getSortFields().isEmpty()) {
            return Sort.unsorted();
        }
        List<Sort.Order> sortOrders = request.getSortFields().stream()
                .map(sortField -> new Sort.Order(sortField.getSortDirection(), sortField.getField()))
                .toList();
        return Sort.by(sortOrders);
    }
}
