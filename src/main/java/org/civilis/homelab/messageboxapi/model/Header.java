package org.civilis.homelab.messageboxapi.model;

import lombok.Data;

import java.util.Date;

@Data
public class Header {
    private Long id;
    private String productId;
    private String username;
    private Date dateTime;
    private String status;
}
