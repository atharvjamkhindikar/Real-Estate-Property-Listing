package com.demo.real_estate_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.real_estate_backend.dto.ApiResponse;
import com.demo.real_estate_backend.model.Subscription;
import com.demo.real_estate_backend.model.SubscriptionType;
import com.demo.real_estate_backend.service.SubscriptionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class SubscriptionController {
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Subscription>> getUserSubscription(@PathVariable Long userId) {
        return subscriptionService.getUserSubscription(userId)
                .map(subscription -> ResponseEntity.ok(ApiResponse.success(subscription)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("No subscription found for user: " + userId)));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Subscription>> createSubscription(
            @RequestParam Long userId,
            @RequestParam SubscriptionType planType) {
        try {
            Subscription subscription = subscriptionService.createSubscription(userId, planType);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Subscription created successfully", subscription));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PatchMapping("/user/{userId}/upgrade")
    public ResponseEntity<ApiResponse<Subscription>> upgradeSubscription(
            @PathVariable Long userId,
            @RequestParam SubscriptionType newPlanType) {
        try {
            Subscription subscription = subscriptionService.upgradeSubscription(userId, newPlanType);
            return ResponseEntity.ok(ApiResponse.success("Subscription upgraded successfully", subscription));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PatchMapping("/user/{userId}/cancel")
    public ResponseEntity<ApiResponse<Subscription>> cancelSubscription(@PathVariable Long userId) {
        try {
            Subscription subscription = subscriptionService.cancelSubscription(userId);
            return ResponseEntity.ok(ApiResponse.success("Subscription cancelled successfully", subscription));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PatchMapping("/user/{userId}/renew")
    public ResponseEntity<ApiResponse<Subscription>> renewSubscription(@PathVariable Long userId) {
        try {
            Subscription subscription = subscriptionService.renewSubscription(userId);
            return ResponseEntity.ok(ApiResponse.success("Subscription renewed successfully", subscription));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PatchMapping("/user/{userId}/auto-renew")
    public ResponseEntity<ApiResponse<Subscription>> toggleAutoRenew(@PathVariable Long userId) {
        try {
            Subscription subscription = subscriptionService.toggleAutoRenew(userId);
            String message = subscription.getAutoRenew() ? "Auto-renew enabled" : "Auto-renew disabled";
            return ResponseEntity.ok(ApiResponse.success(message, subscription));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> isSubscriptionActive(@PathVariable Long userId) {
        boolean isActive = subscriptionService.isSubscriptionActive(userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("isActive", isActive)));
    }
    
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Subscription>>> getActiveSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getActiveSubscriptions();
        return ResponseEntity.ok(ApiResponse.success(subscriptions));
    }
    
    @GetMapping("/expiring")
    public ResponseEntity<ApiResponse<List<Subscription>>> getExpiringSubscriptions(
            @RequestParam(defaultValue = "7") int days) {
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsExpiringWithinDays(days);
        return ResponseEntity.ok(ApiResponse.success(subscriptions));
    }
    
    @PostMapping("/process-expired")
    public ResponseEntity<ApiResponse<Void>> processExpiredSubscriptions() {
        subscriptionService.processExpiredSubscriptions();
        return ResponseEntity.ok(ApiResponse.success("Expired subscriptions processed", null));
    }
}
