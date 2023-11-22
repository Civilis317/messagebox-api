package org.civilis.homelab.messageboxapi.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.civilis.homelab.messageboxapi.model.Header;
import org.civilis.homelab.messageboxapi.model.search.MessagePageResponse;
import org.civilis.homelab.messageboxapi.model.search.PageResponse;
import org.civilis.homelab.messageboxapi.service.MessageBoxService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageBoxController {

    private final MessageBoxService messageBoxService;

    @Operation(summary = "Get message-headers belonging to a selected username."
            , description = "params are username and status, together with paging params.")
    @ApiResponse(responseCode = "200", description = "No errors .")
    @ApiResponse(responseCode = "400", description = "Error due to invalid input.")
    @ApiResponse(responseCode = "500", description = "Error caused by server-side problems.")
    @GetMapping("/headers/{username}")
    public PageResponse<Header> getHeadersByUsername(
            @PathVariable("username") String username,
            @RequestParam("status") String status,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        return messageBoxService.getHeadersByUsername(username, status, page, pageSize);
    }

    @Operation(summary = "Get messages belonging to a selected header."
            , description = "params are header-id and archive indicator, together with paging params.")
    @ApiResponse(responseCode = "200", description = "No errors .")
    @ApiResponse(responseCode = "400", description = "Error due to invalid input.")
    @ApiResponse(responseCode = "500", description = "Error caused by server-side problems.")
    @GetMapping("/list/{headerId}")
    public MessagePageResponse getMessagesByHeaderId(
            @PathVariable("headerId") Long headerId,
            @RequestParam("archive") boolean archive,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {
        return messageBoxService.getMessagesByHeaderId(headerId, archive, page, pageSize);
    }

}
