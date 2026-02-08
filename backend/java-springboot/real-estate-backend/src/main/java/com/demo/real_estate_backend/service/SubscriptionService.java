package com.demo.real_estate_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.real_estate_backend.model.Subscription;
import com.demo.real_estate_backend.model.SubscriptionType;
import com.demo.real_estate_backend.model.User;
import com.demo.real_estate_backend.repository.SubscriptionRepository;
import com.demo.real_estate_backend.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubscriptionService {
    
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private static final BigDecimal BASIC_PRICE = new BigDecimal("9.99");
    private static final BigDecimal PREMIUM_PRICE = new BigDecimal("19.99");
    private static final BigDecimal ENTERPRISE_PRICE = new BigDecimal("49.99");
    
    public Subscription createSubscription(Long userId, SubscriptionType planType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        // Check if user already has a subscription
        if (subscriptionRepository.existsByUserId(userId)) {
            throw new RuntimeException("User already has a subscription. Use upgrade instead.");
        }
        
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlanType(planType);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(calculateEndDate(planType));
        subscription.setPrice(getPlanPrice(planType));
        subscription.setActive(true);
        subscription.setAutoRenew(false); // Default to false

        // Save subscription
        Subscription saved = subscriptionRepository.save(subscription);
        
        // Flush to ensure immediate persistence
        subscriptionRepository.flush();

        // Update user's subscription type
        user.setSubscriptionType(planType);
        userRepository.save(user);
        userRepository.flush();

        return saved;
    }
    
    public Subscription upgradeSubscription(Long userId, SubscriptionType newPlanType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No subscription found for user: " + userId));
        
        if (subscription.getPlanType().ordinal() >= newPlanType.ordinal()) {
            throw new RuntimeException("Can only upgrade to a higher plan");
        }
        
        subscription.setPlanType(newPlanType);
        subscription.setPrice(getPlanPrice(newPlanType));
        subscription.setEndDate(calculateEndDate(newPlanType));
        
        Subscription saved = subscriptionRepository.save(subscription);
        
        // Update user's subscription type
        User user = subscription.getUser();
        user.setSubscriptionType(newPlanType);
        userRepository.save(user);
        
        return saved;
    }
    
    public Subscription cancelSubscription(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No subscription found for user: " + userId));
        
        subscription.setActive(false);
        subscription.setAutoRenew(false);
        
        // Update user's subscription type to FREE
        User user = subscription.getUser();
        user.setSubscriptionType(SubscriptionType.FREE);
        userRepository.save(user);
        
        return subscriptionRepository.save(subscription);
    }
    
    public Subscription renewSubscription(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No subscription found for user: " + userId));
        
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(calculateEndDate(subscription.getPlanType()));
        subscription.setActive(true);
        
        return subscriptionRepository.save(subscription);
    }
    
    public Optional<Subscription> getUserSubscription(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }
    
    public List<Subscription> getActiveSubscriptions() {
        return subscriptionRepository.findByActiveTrue();
    }
    
    public List<Subscription> getExpiredSubscriptions() {
        return subscriptionRepository.findExpiredSubscriptions(LocalDate.now());
    }
    
    public List<Subscription> getSubscriptionsExpiringWithinDays(int days) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(days);
        return subscriptionRepository.findByEndDateBetween(start, end);
    }
    
    public void processExpiredSubscriptions() {
        List<Subscription> expired = getExpiredSubscriptions();
        for (Subscription subscription : expired) {
            if (subscription.getAutoRenew()) {
                renewSubscription(subscription.getUser().getId());
            } else {
                subscription.setActive(false);
                User user = subscription.getUser();
                user.setSubscriptionType(SubscriptionType.FREE);
                userRepository.save(user);
                subscriptionRepository.save(subscription);
            }
        }
    }
    
    public Subscription toggleAutoRenew(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No subscription found for user: " + userId));
        
        subscription.setAutoRenew(!subscription.getAutoRenew());
        return subscriptionRepository.save(subscription);
    }
    
    public boolean isSubscriptionActive(Long userId) {
        Optional<Subscription> subscription = subscriptionRepository.findByUserId(userId);
        return subscription.map(s -> s.getActive() && !s.isExpired()).orElse(false);
    }
    
    private LocalDate calculateEndDate(SubscriptionType planType) {
        return switch (planType) {
            case FREE -> null; // No end date for free
            case BASIC -> LocalDate.now().plusMonths(1);
            case PREMIUM -> LocalDate.now().plusMonths(1);
            case ENTERPRISE -> LocalDate.now().plusYears(1);
        };
    }
    
    private BigDecimal getPlanPrice(SubscriptionType planType) {
        return switch (planType) {
            case FREE -> BigDecimal.ZERO;
            case BASIC -> BASIC_PRICE;
            case PREMIUM -> PREMIUM_PRICE;
            case ENTERPRISE -> ENTERPRISE_PRICE;
        };
    }
}
