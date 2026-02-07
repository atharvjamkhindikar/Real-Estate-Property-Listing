package com.demo.real_estate_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactAgentResponse {

    private Long id;
    private UserResponse user;
    private PropertyResponse property;
    private String subject;
    private String message;
    private String senderName;
    private String senderEmail;
    private String senderPhone;
    private String additionalInfo;
    private LocalDateTime createdAt;
    private LocalDateTime respondedAt;
    private Boolean isRead;
}
