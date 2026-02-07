package com.demo.real_estate_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "schedule_viewings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleViewing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"favorites", "properties", "searchHistories", "subscription", "hibernateLazyInitializer", "handler"})
    private User user;  // The user wanting to view the property

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnoreProperties({"owner", "images", "hibernateLazyInitializer", "handler"})
    private Property property;  // The property to be viewed

    @Column(nullable = false)
    private LocalDate viewingDate;

    @Column(nullable = false)
    private LocalTime viewingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ViewingStatus status = ViewingStatus.PENDING;

    @Column(length = 1000)
    private String notes;  // Any special requests or notes from the user

    @Column(length = 500)
    private String rejectionReason;  // Reason for rejection if status is REJECTED

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime confirmedAt;

    private LocalDateTime rejectedAt;

    private LocalDateTime completedAt;

    private LocalDateTime cancelledAt;
}
