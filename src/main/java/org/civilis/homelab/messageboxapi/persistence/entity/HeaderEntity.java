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
@Table(name = "header")
public class HeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "dateTime", nullable = false)
    private Date dateTime;

    @Column(name = "status", nullable = false)
    private String status;
}
