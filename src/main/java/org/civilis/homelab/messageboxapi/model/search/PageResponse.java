package org.civilis.homelab.messageboxapi.model.search;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonPropertyOrder({"pageNumber", "pageSize", "totalElements", "totalPages", "content"} )
public class PageResponse<T> {

    @NotNull
    private List<T> content;
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
    @NotNull
    private Integer totalElements;
    @NotNull
    private Integer totalPages;
}

