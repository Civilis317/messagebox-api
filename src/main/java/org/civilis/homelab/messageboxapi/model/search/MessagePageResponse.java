package org.civilis.homelab.messageboxapi.model.search;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.civilis.homelab.messageboxapi.model.Header;
import org.civilis.homelab.messageboxapi.model.Message;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"pageNumber", "pageSize", "totalElements", "totalPages","header","content"} )
public class MessagePageResponse extends PageResponse<Message> {
    private Header header;

    public MessagePageResponse(Header header, PageResponse<Message> pageResponse) {
        this.header = header;
        this.setContent(pageResponse.getContent());
        this.setPageNumber(pageResponse.getPageNumber());
        this.setPageSize(pageResponse.getPageSize());
        this.setTotalPages(pageResponse.getTotalPages());
        this.setTotalElements(pageResponse.getTotalElements());
    }

}
