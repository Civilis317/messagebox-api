package org.civilis.homelab.messageboxapi.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "sender", nullable = false)
    private String sender;

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "dateTime", nullable = false)
    private Date dateTime;

    @Column(name = "kind", nullable = false)
    private String kind;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "content")
    private String content;
}
