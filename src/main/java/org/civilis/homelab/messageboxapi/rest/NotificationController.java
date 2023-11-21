package org.civilis.homelab.messageboxapi.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.civilis.homelab.messageboxapi.exception.NotAuthorizedException;
import org.civilis.homelab.messageboxapi.exception.ValidationException;
import org.civilis.homelab.messageboxapi.model.Notification;
import org.civilis.homelab.messageboxapi.service.MessageBoxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpHeaders;

@Slf4j
@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController extends AbstractController {

    @Value("${auth.api-key}")
    private String apiKey;

    private final static String API_KEY_HEADER = "Authorization";

    private final MessageBoxService messageBoxService;

    @PostMapping("/")
    public Notification receiveNotification (@RequestBody Notification notification, HttpServletRequest request) {
        checkAuthHeader(request);
        return messageBoxService.processNotification(notification);
    }

    private void checkAuthHeader(HttpServletRequest request) {
        String authHeaderValue = request.getHeader(API_KEY_HEADER);
        if (authHeaderValue == null || ! apiKey.equals(authHeaderValue)) {
            throw new NotAuthorizedException();
        }
    }
}
