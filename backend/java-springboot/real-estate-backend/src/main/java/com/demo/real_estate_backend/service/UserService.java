package com.demo.real_estate_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.real_estate_backend.dto.PageResponse;
import com.demo.real_estate_backend.dto.UserRequest;
import com.demo.real_estate_backend.dto.UserResponse;
import com.demo.real_estate_backend.model.Role;
import com.demo.real_estate_backend.model.SubscriptionType;
import com.demo.real_estate_backend.model.User;
import com.demo.real_estate_backend.model.UserType;
import com.demo.real_estate_backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public PageResponse<UserResponse> getAllUsersPaged(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("ASC") 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> userPage = userRepository.findAll(pageable);
        
        return buildPageResponse(userPage);
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User createUser(UserRequest userRequest) {
        // Password is required for new users
        if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
            throw new RuntimeException("Password is required for new users");
        }
        
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + userRequest.getEmail());
        }

        User user = new User();             // added for default free
        // Ensure subscriptionType is set to a default value if not provided
        user.setSubscriptionType(userRequest.getSubscriptionType() != null ? userRequest.getSubscriptionType() : SubscriptionType.FREE);

        mapRequestToUser(userRequest, user);
        return userRepository.save(user);
    }
    
    public User updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        // Check if email is being changed and if new email already exists
        if (!user.getEmail().equals(userRequest.getEmail())) {
            if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists: " + userRequest.getEmail());
            }
        }
        
        mapRequestToUser(userRequest, user);
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
    
    public User activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(true);
        return userRepository.save(user);
    }
    
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(false);
        return userRepository.save(user);
    }
    
    public List<User> getUsersByType(UserType userType) {
        return userRepository.findByUserType(userType);
    }
    
    public List<User> getActiveUsers() {
        return userRepository.findByActiveTrue();
    }
    
    public List<User> getActiveUsersByType(UserType userType) {
        return userRepository.findByUserTypeAndActiveTrue(userType);
    }
    
    public User updateUserRole(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setRole(role);
        return userRepository.save(user);
    }
    
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .role(user.getRole())
                .subscriptionType(user.getSubscriptionType())
                .company(user.getCompany())
                .licenseNumber(user.getLicenseNumber())
                .bio(user.getBio())
                .profileImageUrl(user.getProfileImageUrl())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .propertyCount(user.getProperties() != null ? user.getProperties().size() : 0)
                .favoriteCount(user.getFavorites() != null ? user.getFavorites().size() : 0)
                .build();
    }
    
    private void mapRequestToUser(UserRequest request, User user) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        // Only update password if provided (not empty)
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword()); // In production, hash this!
        }
        user.setPhone(request.getPhone());
        user.setUserType(request.getUserType() != null ? request.getUserType() : UserType.BUYER);
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);
        user.setSubscriptionType(request.getSubscriptionType() != null ? request.getSubscriptionType() : SubscriptionType.FREE);
        user.setCompany(request.getCompany());
        user.setLicenseNumber(request.getLicenseNumber());
        user.setBio(request.getBio());
        user.setProfileImageUrl(request.getProfileImageUrl());
    }
    
    private PageResponse<UserResponse> buildPageResponse(Page<User> page) {
        List<UserResponse> content = page.getContent().stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
        
        return PageResponse.<UserResponse>builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
