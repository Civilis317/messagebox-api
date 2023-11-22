package org.civilis.homelab.messageboxapi.model;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;
    private Long headerId;
    private Long notificationId;
    private Date dateTime;
    private String kind;
    private String sender;
    private String recipient;
    private String documentId;
    private String content;
    private String status;
    private boolean archive;
}
