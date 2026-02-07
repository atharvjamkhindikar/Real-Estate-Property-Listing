package com.demo.real_estate_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.real_estate_backend.model.ContactAgent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactAgentRepository extends JpaRepository<ContactAgent, Long> {

    List<ContactAgent> findByPropertyId(Long propertyId);

    List<ContactAgent> findByUserId(Long userId);

    List<ContactAgent> findByPropertyIdOrderByCreatedAtDesc(Long propertyId);

    List<ContactAgent> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<ContactAgent> findByIsReadFalse();

    List<ContactAgent> findByIsReadFalseAndPropertyOwnerId(Long ownerId);

    @Query("SELECT c FROM ContactAgent c WHERE c.property.owner.id = :ownerId ORDER BY c.createdAt DESC")
    List<ContactAgent> findAllContactsForPropertyOwner(@Param("ownerId") Long ownerId);

    @Query("SELECT c FROM ContactAgent c WHERE c.createdAt >= :startDate AND c.createdAt <= :endDate ORDER BY c.createdAt DESC")
    List<ContactAgent> findContactsInDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    long countByPropertyIdAndCreatedAtAfter(Long propertyId, LocalDateTime startDate);

    long countByIsReadFalseAndPropertyOwnerId(Long ownerId);
}
