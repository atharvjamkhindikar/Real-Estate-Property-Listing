package com.demo.real_estate_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.real_estate_backend.model.Subscription;
import com.demo.real_estate_backend.model.SubscriptionType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    Optional<Subscription> findByUserId(Long userId);
    
    List<Subscription> findByPlanType(SubscriptionType planType);
    
    List<Subscription> findByActiveTrue();
    
    List<Subscription> findByActiveFalse();
    
    List<Subscription> findByEndDateBefore(LocalDate date);
    
    List<Subscription> findByEndDateBetween(LocalDate start, LocalDate end);
    
    @Query("SELECT s FROM Subscription s WHERE s.active = true AND s.endDate < :date")
    List<Subscription> findExpiredSubscriptions(@Param("date") LocalDate date);
    
    @Query("SELECT s FROM Subscription s WHERE s.autoRenew = true AND s.endDate BETWEEN :start AND :end")
    List<Subscription> findSubscriptionsToRenew(@Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.planType = :planType AND s.active = true")
    Long countActiveByPlanType(@Param("planType") SubscriptionType planType);
    
    boolean existsByUserId(Long userId);
}
