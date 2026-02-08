package com.demo.real_estate_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.demo.real_estate_backend.model.ListingType;
import com.demo.real_estate_backend.model.PropertyType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResponse {
	
	private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private PropertyType propertyType;
    private ListingType listingType;
    private Integer bedrooms;
    private Integer bathrooms;
    private BigDecimal squareFeet;
    private Integer yearBuilt;
    private Boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private OwnerSummary owner;
    private BuilderGroupSummary builderGroup;
    private List<String> imageUrls;
    private Long favoriteCount;
    private Boolean isFavorited;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnerSummary {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
        private String company;
        private String profileImageUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BuilderGroupSummary {
        private Long id;
        private String name;
        private String description;
    }

}
