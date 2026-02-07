package com.demo.real_estate_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactAgentRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Message is required")
    private String message;

    @NotBlank(message = "Sender name is required")
    private String senderName;

    @Email(message = "Sender email should be valid")
    @NotBlank(message = "Sender email is required")
    private String senderEmail;

    @NotBlank(message = "Sender phone is required")
    private String senderPhone;

    private String additionalInfo;
}
