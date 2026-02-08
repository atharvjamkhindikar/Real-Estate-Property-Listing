package com.demo.real_estate_backend.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleViewingRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Viewing date is required")
    @Future(message = "Viewing date must be in the future")
    private LocalDate viewingDate;

    @NotNull(message = "Viewing time is required")
    private LocalTime viewingTime;

    private String notes;
}
