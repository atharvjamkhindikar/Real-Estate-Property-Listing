package com.demo.real_estate_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.demo.real_estate_backend.model.ViewingStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleViewingResponse {

    private Long id;
    private UserResponse user;
    private PropertyResponse property;
    private LocalDate viewingDate;
    private LocalTime viewingTime;
    private ViewingStatus status;
    private String notes;
    private String rejectionReason;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime completedAt;
    private LocalDateTime cancelledAt;
}
