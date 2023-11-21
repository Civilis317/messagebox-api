package org.civilis.homelab.messageboxapi.model;

import lombok.Data;

import java.util.Date;

@Data
public class Notification {
    private Long id;
    private String productId;
    private String sender;
    private String recipient;
    private Date dateTime;
    private String kind;
    private String documentId;
    private String content;
}
