package com.demo.real_estate_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "property_images")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PropertyImage {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String imageUrl;
    
    @Column(length = 500)
    private String caption;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean isPrimary = false;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    // Many-to-One relationship: Many images belong to one property
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @PrePersist
    protected void onCreate() {
        if (uploadedAt == null) {
            uploadedAt = LocalDateTime.now();
        }
    }

}
