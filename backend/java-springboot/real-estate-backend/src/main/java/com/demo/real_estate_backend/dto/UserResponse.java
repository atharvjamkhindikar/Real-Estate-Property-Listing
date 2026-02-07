package com.demo.real_estate_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.demo.real_estate_backend.model.Role;
import com.demo.real_estate_backend.model.SubscriptionType;
import com.demo.real_estate_backend.model.UserType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phone;
    private UserType userType;
    private Role role;
    private SubscriptionType subscriptionType;
    private String company;
    private String licenseNumber;
    private String bio;
    private String profileImageUrl;
    private Boolean active;
    private LocalDateTime createdAt;
    
    private Integer propertyCount;
    private Integer favoriteCount;
}
