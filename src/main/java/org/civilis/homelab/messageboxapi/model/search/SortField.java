package org.civilis.homelab.messageboxapi.model.search;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Optional, if not provided the ordering will be provided by the database, see filter object for valid sort fields")
public class SortField {

    @Schema(example = "id")
    @NotBlank
    private String field;

    @EqualsAndHashCode.Exclude
    @NotNull
    private Sort.Direction sortDirection;
}
