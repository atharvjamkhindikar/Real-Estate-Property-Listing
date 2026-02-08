package com.demo.real_estate_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.real_estate_backend.model.PropertyImage;

import java.util.List;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    
    List<PropertyImage> findByPropertyId(Long propertyId);
    
    List<PropertyImage> findByPropertyIdOrderByDisplayOrderAsc(Long propertyId);
    
    List<PropertyImage> findByPropertyIdAndIsPrimaryTrue(Long propertyId);

    @Query("SELECT MAX(pi.displayOrder) FROM PropertyImage pi WHERE pi.property.id = :propertyId")
    Integer findMaxDisplayOrderByPropertyId(@Param("propertyId") Long propertyId);

    @Query("SELECT pi FROM PropertyImage pi JOIN FETCH pi.property WHERE pi.property.id = :propertyId")
    List<PropertyImage> findByPropertyIdWithProperty(@Param("propertyId") Long propertyId);
}
