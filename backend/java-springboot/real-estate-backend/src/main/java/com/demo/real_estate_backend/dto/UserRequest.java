package com.demo.real_estate_backend.dto;

import com.demo.real_estate_backend.model.Role;
import com.demo.real_estate_backend.model.SubscriptionType;
import com.demo.real_estate_backend.model.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    
    @NotBlank(message = "First name is required")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "Phone is required")
    private String phone;
    
    private UserType userType = UserType.BUYER;
    private Role role = Role.USER;
    private SubscriptionType subscriptionType = SubscriptionType.FREE;
    
    private String company;
    private String licenseNumber;
    private String bio;
    private String profileImageUrl;
}
