package com.demo.real_estate_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnoreProperties({"favorites", "properties", "searchHistories", "subscription", "hibernateLazyInitializer", "handler"})
    private User user;

    @NotNull(message = "Plan type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionType planType = SubscriptionType.FREE;
    
    @Column(nullable = false)
    private LocalDate startDate = LocalDate.now();
    
    private LocalDate endDate;
    
    private BigDecimal price;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(nullable = false)
    private Boolean autoRenew = false;
    
    private String paymentMethod;
    
    private String transactionId;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public boolean isExpired() {
        return endDate != null && LocalDate.now().isAfter(endDate);
    }

}
