package org.civilis.homelab.messageboxapi.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.civilis.homelab.messageboxapi.model.Header;
import org.civilis.homelab.messageboxapi.model.Message;
import org.civilis.homelab.messageboxapi.model.search.FilterPageRequest;
import org.civilis.homelab.messageboxapi.model.search.PageResponse;
import org.civilis.homelab.messageboxapi.service.MessageBoxService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageBoxController {

    private final MessageBoxService messageBoxService;

    @Operation(summary = "Find message headers that meet search criteria. NB: In Swagger-UI the GET does not work"
            ,description = "See https://swagger.io/docs/specification/describing-request-body/")
    @ApiResponse(responseCode = "200", description = "No errors (if nothing matches the criteria an empty list is returned.")
    @ApiResponse(responseCode = "400", description = "Error due to invalid input.")
    @ApiResponse(responseCode = "500", description = "Error caused by server-side problems.")
    @GetMapping(value = "/headers")
    public PageResponse<Header> listByGet(@Valid @RequestBody FilterPageRequest<Header> request) {
        return messageBoxService.getHeaderPage(request);
    }

    @Operation(summary = "Find message headers that meet search criteria."
            ,description = "See https://swagger.io/docs/specification/describing-request-body/")
    @ApiResponse(responseCode = "200", description = "No errors (if nothing matches the criteria an empty list is returned.")
    @ApiResponse(responseCode = "400", description = "Error due to invalid input.")
    @ApiResponse(responseCode = "500", description = "Error caused by server-side problems.")
    @PostMapping(value = "/headers")
    public PageResponse<Header> listByPost(@Valid @RequestBody FilterPageRequest<Header> request) {
        return messageBoxService.getHeaderPage(request);
    }

    @Operation(summary = "Find messages that meet search criteria. NB: In Swagger-UI the GET does not work"
            ,description = "See https://swagger.io/docs/specification/describing-request-body/")
    @ApiResponse(responseCode = "200", description = "No errors (if nothing matches the criteria an empty list is returned.")
    @ApiResponse(responseCode = "400", description = "Error due to invalid input.")
    @ApiResponse(responseCode = "500", description = "Error caused by server-side problems.")
    @GetMapping(value = "/list")
    public PageResponse<Message> messagesByGet(@Valid @RequestBody FilterPageRequest<Message> request) {
        return messageBoxService.getMessagePage(request);
    }


    @Operation(summary = "Find messages that meet search criteria."
            ,description = "See https://swagger.io/docs/specification/describing-request-body/")
    @ApiResponse(responseCode = "200", description = "No errors (if nothing matches the criteria an empty list is returned.")
    @ApiResponse(responseCode = "400", description = "Error due to invalid input.")
    @ApiResponse(responseCode = "500", description = "Error caused by server-side problems.")
    @PostMapping(value = "/list")
    public PageResponse<Message> messagesByPost(@Valid @RequestBody FilterPageRequest<Message> request) {
        return messageBoxService.getMessagePage(request);
    }

}
