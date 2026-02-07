package com.demo.real_estate_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.real_estate_backend.dto.PropertyImageRequest;
import com.demo.real_estate_backend.dto.PropertyImageResponse;
import com.demo.real_estate_backend.exception.ResourceNotFoundException;
import com.demo.real_estate_backend.model.Property;
import com.demo.real_estate_backend.model.PropertyImage;
import com.demo.real_estate_backend.repository.PropertyImageRepository;
import com.demo.real_estate_backend.repository.PropertyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropertyImageService {

    @Autowired
    private PropertyImageRepository propertyImageRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public PropertyImageResponse addImage(Long propertyId, PropertyImageRequest request) {
        // Validate imageUrl is provided
        if (request.getImageUrl() == null || request.getImageUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL is required");
        }

        // Get the property
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", propertyId));

        // Get the next display order (handle null when no images exist)
        Integer maxOrderNullable = propertyImageRepository.findMaxDisplayOrderByPropertyId(propertyId);
        int maxOrder = (maxOrderNullable != null) ? maxOrderNullable : -1;
        int nextOrder = maxOrder + 1;

        PropertyImage image = PropertyImage.builder()
                .imageUrl(request.getImageUrl())
                .caption(request.getCaption())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : nextOrder)
                .isPrimary(request.getIsPrimary() != null ? request.getIsPrimary() : false)
                .property(property)
                .build();

        PropertyImage savedImage = propertyImageRepository.save(image);
        return toPropertyImageResponse(savedImage);
    }

    public PropertyImageResponse updateImage(Long imageId, PropertyImageRequest request) {
        PropertyImage image = propertyImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("PropertyImage", "id", imageId));

        // Update imageUrl only if provided
        if (request.getImageUrl() != null && !request.getImageUrl().trim().isEmpty()) {
            image.setImageUrl(request.getImageUrl());
        }

        image.setCaption(request.getCaption());
        if (request.getDisplayOrder() != null) {
            image.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getIsPrimary() != null) {
            image.setIsPrimary(request.getIsPrimary());
        }

        PropertyImage updatedImage = propertyImageRepository.save(image);
        return toPropertyImageResponse(updatedImage);
    }

    public void deleteImage(Long imageId) {
        if (!propertyImageRepository.existsById(imageId)) {
            throw new ResourceNotFoundException("PropertyImage", "id", imageId);
        }
        propertyImageRepository.deleteById(imageId);
    }

    // Updated setPrimaryImage to use the new repository method
    public PropertyImageResponse setPrimaryImage(Long propertyId, Long imageId) {
        List<PropertyImage> images = propertyImageRepository.findByPropertyIdWithProperty(propertyId);

        PropertyImage image = images.stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("PropertyImage", "id", imageId));

        // Set all images for this property to non-primary
        images.forEach(img -> img.setIsPrimary(false));

        // Set this image as primary
        image.setIsPrimary(true);
        PropertyImage updated = propertyImageRepository.save(image);

        return toPropertyImageResponse(updated);
    }

    // Updated reorderImages to use the new repository method
    public void reorderImages(Long propertyId, List<Long> imageIds) {
        List<PropertyImage> images = propertyImageRepository.findByPropertyIdWithProperty(propertyId);

        for (int i = 0; i < imageIds.size(); i++) {
            Long imageId = imageIds.get(i);
            PropertyImage image = images.stream()
                    .filter(img -> img.getId().equals(imageId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("PropertyImage", "id", imageId));

            image.setDisplayOrder(i);
            propertyImageRepository.save(image);
        }
    }

    public List<PropertyImageResponse> getPropertyImages(Long propertyId) {
        return propertyImageRepository.findByPropertyIdOrderByDisplayOrderAsc(propertyId)
                .stream()
                .map(this::toPropertyImageResponse)
                .collect(Collectors.toList());
    }

    public PropertyImageResponse toPropertyImageResponse(PropertyImage image) {
        return PropertyImageResponse.builder()
                .id(image.getId())
                .imageUrl(image.getImageUrl())
                .caption(image.getCaption())
                .isPrimary(image.getIsPrimary())
                .displayOrder(image.getDisplayOrder())
                .uploadedAt(image.getUploadedAt())
                .build();
    }
}
