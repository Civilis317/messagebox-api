package org.civilis.homelab.messageboxapi.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.civilis.homelab.messageboxapi.exception.ApplicationException;
import org.civilis.homelab.messageboxapi.exception.NotAuthorizedException;
import org.civilis.homelab.messageboxapi.exception.NotFoundException;
import org.civilis.homelab.messageboxapi.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
@OpenAPIDefinition(
        servers = {
                @Server(url = "${server.servlet.context-path}", description = "${app.name}")
        }
)
public abstract class AbstractController {

    @Value("${app.service}")
    private String service;

    @Value("${app.name}")
    private String description;

    @Value("${app.version}")
    private String version;

    @ExceptionHandler
    protected void handleValidationException(ValidationException e, HttpServletResponse response) throws IOException {
        log.warn("Validation failed for: {}", e.getMessagesAsString());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessagesAsString());
    }

    @ExceptionHandler
    protected void handleNotAuthorizedException(NotAuthorizedException e, HttpServletResponse response) throws IOException {
        log.warn("Authorization failed");
        response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler
    protected void handleNotFoundException(NotFoundException e, HttpServletResponse response) throws IOException {
        log.info("NotFoundException: {}", e.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    // in case of http 500 internal server error, no information about the exception is to be revealed, so a different approach is used.
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handle(ApplicationException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }

}
