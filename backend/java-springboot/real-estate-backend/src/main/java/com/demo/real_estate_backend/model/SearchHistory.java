package com.demo.real_estate_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_history", indexes = {
    @Index(name = "idx_search_user", columnList = "user_id"),
    @Index(name = "idx_search_timestamp", columnList = "searchedAt")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistory {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"favorites", "properties", "searchHistories", "subscription", "hibernateLazyInitializer", "handler"})
    private User user;
    
    // Search criteria stored as individual fields for analytics
    private String searchCity;
    private String searchState;
    
    @Enumerated(EnumType.STRING)
    private PropertyType searchPropertyType;
    
    @Enumerated(EnumType.STRING)
    private ListingType searchListingType;
    
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    
    private Integer minBedrooms;
    private Integer maxBedrooms;
    
    private Integer minBathrooms;
    private Integer maxBathrooms;
    
    private BigDecimal minSquareFeet;
    private BigDecimal maxSquareFeet;
    
    // Full search criteria as JSON for complex queries
    @Column(length = 2000)
    private String searchCriteria;
    
    private Integer resultsCount;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime searchedAt = LocalDateTime.now();

}
