package org.civilis.homelab.messageboxapi.model.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.LinkedHashSet;

@Data
public class FilterPageRequest<T> {

    @Schema(description = "Optional, if not provided all records for the specified page will be returned")
    private T filter;

    @NotNull(message = "Field @@FIELD@@ is mandatory")
    @Min(value = 1, message = "Field @@FIELD@@ should be >= 1")
    private Integer page;

    @NotNull(message = "Field @@FIELD@@ is mandatory")
    @Min(value = 1, message = "Field @@FIELD@@ should be >= 1")
    @Max(value = 100, message = "Field @@FIELD@@ should be <= 100")
    @Schema(defaultValue = "10")
    private Integer pageSize;

    private LinkedHashSet<SortField> sortFields;
}
