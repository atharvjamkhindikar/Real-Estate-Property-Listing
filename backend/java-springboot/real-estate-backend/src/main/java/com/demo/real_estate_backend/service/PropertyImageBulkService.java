package com.demo.real_estate_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.real_estate_backend.model.Property;
import com.demo.real_estate_backend.model.PropertyImage;
import com.demo.real_estate_backend.repository.PropertyImageRepository;
import com.demo.real_estate_backend.repository.PropertyRepository;

import java.util.List;

/**
 * Service to bulk add sample images to properties without images
 */
@Service
@Transactional
public class PropertyImageBulkService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyImageRepository propertyImageRepository;

    // Sample property images - free stock images
    private static final String[] PROPERTY_IMAGES = {
        "https://images.unsplash.com/photo-1570129477492-45c003cedd38?w=800&q=80",     // Living room
        "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800&q=80",     // Modern house
        "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800&q=80",        // Bedroom
        "https://images.unsplash.com/photo-1552321554-5fefe8c9ef14?w=800&q=80",        // Kitchen
        "https://images.unsplash.com/photo-1536484405104-37ce4d1f2e2f?w=800&q=80",     // Bathroom
        "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=800&q=80",        // Dining room
        "https://images.unsplash.com/photo-1493857671505-72967e2e2760?w=800&q=80",     // Exterior
        "https://images.unsplash.com/photo-1512917774080-9bc841ebc329?w=800&q=80",     // Pool
        "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=800&q=80",     // Modern apartment
        "https://images.unsplash.com/photo-1522840985259-a9db49f1dba4?w=800&q=80"      // Office space
    };

    private static final String[] IMAGE_CAPTIONS = {
        "Spacious living room with natural lighting",
        "Modern exterior view of the property",
        "Master bedroom with elegant furnishings",
        "Well-equipped kitchen with modern appliances",
        "Luxurious bathroom design",
        "Dining area overlooking the garden",
        "Beautiful property exterior",
        "Resort-style pool area",
        "Contemporary apartment living space",
        "Professional office setup"
    };

    /**
     * Add sample images to all properties that don't have any images
     * @return Number of properties updated with images
     */
    public int addImagesToPropertiesWithoutImages() {
        List<Property> allProperties = propertyRepository.findAll();
        int propertiesUpdated = 0;

        for (int i = 0; i < allProperties.size(); i++) {
            Property property = allProperties.get(i);

            // Check if property has any images
            List<PropertyImage> existingImages = propertyImageRepository.findByPropertyId(property.getId());

            if (existingImages.isEmpty()) {
                // Add images to this property
                addImagesToProperty(property, i);
                propertiesUpdated++;
            }
        }

        return propertiesUpdated;
    }

    /**
     * Add sample images to a specific property
     * @param property Property to add images to
     * @param propertyIndex Index for selecting different images
     */
    private void addImagesToProperty(Property property, int propertyIndex) {
        // Add 3-5 images per property
        int numberOfImages = 3 + (propertyIndex % 3); // 3, 4, or 5 images

        for (int i = 0; i < numberOfImages; i++) {
            // Rotate through available images
            int imageIndex = (propertyIndex + i) % PROPERTY_IMAGES.length;

            PropertyImage image = PropertyImage.builder()
                    .property(property)
                    .imageUrl(PROPERTY_IMAGES[imageIndex])
                    .caption(IMAGE_CAPTIONS[imageIndex])
                    .displayOrder(i)
                    .isPrimary(i == 0)  // First image is primary
                    .build();

            propertyImageRepository.save(image);
        }
    }

    /**
     * Add a specific number of images to a property
     * @param propertyId Property ID
     * @param numberOfImages Number of images to add
     * @return Number of images added
     */
    public int addImagesToSpecificProperty(Long propertyId, int numberOfImages) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id: " + propertyId));

        // Check if property already has images
        List<PropertyImage> existingImages = propertyImageRepository.findByPropertyId(propertyId);
        int startingDisplayOrder = existingImages.size();

        int imagesAdded = 0;
        for (int i = 0; i < numberOfImages && i < PROPERTY_IMAGES.length; i++) {
            PropertyImage image = PropertyImage.builder()
                    .property(property)
                    .imageUrl(PROPERTY_IMAGES[i])
                    .caption(IMAGE_CAPTIONS[i])
                    .displayOrder(startingDisplayOrder + i)
                    .isPrimary(startingDisplayOrder == 0 && i == 0)  // First image is primary if no images exist
                    .build();

            propertyImageRepository.save(image);
            imagesAdded++;
        }

        return imagesAdded;
    }

    /**
     * Bulk add images to all properties
     * @return Total number of images added
     */
    public int addImagesToAllProperties() {
        List<Property> allProperties = propertyRepository.findAll();
        int totalImagesAdded = 0;

        for (int i = 0; i < allProperties.size(); i++) {
            Property property = allProperties.get(i);

            // Clear existing images first
            List<PropertyImage> existingImages = propertyImageRepository.findByPropertyId(property.getId());
            propertyImageRepository.deleteAll(existingImages);

            // Add new images
            int numberOfImages = 3 + (i % 3);
            totalImagesAdded += addImagesToProperty(property, i, numberOfImages);
        }

        return totalImagesAdded;
    }

    /**
     * Helper method to add specific number of images
     */
    private int addImagesToProperty(Property property, int propertyIndex, int numberOfImages) {
        int imagesAdded = 0;

        for (int i = 0; i < numberOfImages; i++) {
            int imageIndex = (propertyIndex + i) % PROPERTY_IMAGES.length;

            PropertyImage image = PropertyImage.builder()
                    .property(property)
                    .imageUrl(PROPERTY_IMAGES[imageIndex])
                    .caption(IMAGE_CAPTIONS[imageIndex])
                    .displayOrder(i)
                    .isPrimary(i == 0)
                    .build();

            propertyImageRepository.save(image);
            imagesAdded++;
        }

        return imagesAdded;
    }
}
