package org.civilis.homelab.messageboxapi.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.civilis.homelab.messageboxapi.model.Notification;
import org.civilis.homelab.messageboxapi.service.MessageBoxService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController extends AbstractController {

    private final MessageBoxService messageBoxService;

    @PostMapping("/")
    public Notification receiveNotification (@RequestBody Notification notification) {
        return messageBoxService.processNotification(notification);
    }
}
