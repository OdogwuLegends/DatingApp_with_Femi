package com.legends.promiscuous.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Notification")
@Setter
@Getter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT",length = 1000)
    private String content;

    private Long senderId;

    @ManyToOne
    private User user;
}
