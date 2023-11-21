package org.civilis.homelab.messageboxapi.model;

import lombok.Data;

@Data
public class Message {
    private Long id;
    private String kind;
    private String sender;
    private String recipient;
    private String documentId;
    private String content;
    private String status;
    private boolean archive;
}
