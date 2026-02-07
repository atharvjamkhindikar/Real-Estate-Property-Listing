package com.demo.real_estate_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_agents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"favorites", "properties", "searchHistories", "subscription", "hibernateLazyInitializer", "handler"})
    private User user;  // The user contacting the agent

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnoreProperties({"owner", "images", "hibernateLazyInitializer", "handler"})
    private Property property;  // The property being enquired about

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private String senderEmail;

    @Column(nullable = false)
    private String senderPhone;

    @Column(columnDefinition = "TEXT")
    private String additionalInfo;  // For storing address, preferred viewing times, etc.

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime respondedAt;

    private Boolean isRead = false;
}
