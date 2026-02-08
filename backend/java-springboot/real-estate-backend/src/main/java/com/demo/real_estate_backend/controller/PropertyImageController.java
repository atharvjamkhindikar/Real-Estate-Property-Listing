package com.demo.real_estate_backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.demo.real_estate_backend.dto.ApiResponse;
import com.demo.real_estate_backend.dto.PropertyImageRequest;
import com.demo.real_estate_backend.dto.PropertyImageResponse;
import com.demo.real_estate_backend.service.FileUploadService;
import com.demo.real_estate_backend.service.PropertyImageBulkService;
import com.demo.real_estate_backend.service.PropertyImageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class PropertyImageController {

    @Autowired
    private PropertyImageService propertyImageService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private PropertyImageBulkService propertyImageBulkService;

    /**
     * Upload image file (multipart/form-data)
     * @param propertyId Property ID
     * @param file Image file to upload
     * @param caption Optional image caption
     * @param isPrimary Optional flag to set as primary
     */
    @PostMapping("/{propertyId}/images/upload")
    public ResponseEntity<ApiResponse<PropertyImageResponse>> uploadImage(
            @PathVariable Long propertyId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "caption", required = false) String caption,
            @RequestParam(value = "isPrimary", required = false) Boolean isPrimary) {
        try {
            // Upload file and get path
            String imageUrl = fileUploadService.uploadImage(file);

            // Create PropertyImageRequest with uploaded file path
            PropertyImageRequest request = PropertyImageRequest.builder()
                    .imageUrl(imageUrl)
                    .caption(caption)
                    .isPrimary(isPrimary != null ? isPrimary : false)
                    .build();

            // Add image to property
            PropertyImageResponse image = propertyImageService.addImage(propertyId, request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Image uploaded and added successfully", image));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to upload image: " + e.getMessage()));
        } catch (RuntimeException e) {
            // Catches both IllegalArgumentException and other RuntimeExceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Add image with URL (JSON request)
     * @param propertyId Property ID
     * @param request PropertyImageRequest with imageUrl
     */
    @PostMapping("/{propertyId}/images")
    public ResponseEntity<ApiResponse<PropertyImageResponse>> addImage(
            @PathVariable Long propertyId,
            @Valid @RequestBody PropertyImageRequest request) {
        try {
            PropertyImageResponse image = propertyImageService.addImage(propertyId, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Image added successfully", image));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{propertyId}/images")
    public ResponseEntity<ApiResponse<List<PropertyImageResponse>>> getPropertyImages(@PathVariable Long propertyId) {
        try {
            List<PropertyImageResponse> images = propertyImageService.getPropertyImages(propertyId);
            return ResponseEntity.ok(ApiResponse.success(images));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{propertyId}/images/{imageId}")
    public ResponseEntity<ApiResponse<PropertyImageResponse>> updateImage(
            @PathVariable Long propertyId,
            @PathVariable Long imageId,
            @Valid @RequestBody PropertyImageRequest request) {
        try {
            PropertyImageResponse image = propertyImageService.updateImage(imageId, request);
            return ResponseEntity.ok(ApiResponse.success("Image updated successfully", image));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{propertyId}/images/{imageId}")
    public ResponseEntity<ApiResponse<Void>> deleteImage(
            @PathVariable Long propertyId,
            @PathVariable Long imageId) {
        try {
            propertyImageService.deleteImage(imageId);
            return ResponseEntity.ok(ApiResponse.success("Image deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PatchMapping("/{propertyId}/images/{imageId}/primary")
    public ResponseEntity<ApiResponse<PropertyImageResponse>> setPrimaryImage(
            @PathVariable Long propertyId,
            @PathVariable Long imageId) {
        try {
            PropertyImageResponse image = propertyImageService.setPrimaryImage(propertyId, imageId);
            return ResponseEntity.ok(ApiResponse.success("Primary image set successfully", image));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{propertyId}/images/reorder")
    public ResponseEntity<ApiResponse<Void>> reorderImages(
            @PathVariable Long propertyId,
            @RequestBody List<Long> imageIds) {
        try {
            propertyImageService.reorderImages(propertyId, imageIds);
            return ResponseEntity.ok(ApiResponse.success("Images reordered successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * BULK OPERATIONS
     */

    /**
     * Add sample images to all properties that don't have images
     * (Admin endpoint - can be called manually)
     */
    @PostMapping("/bulk/add-images-to-empty-properties")
    public ResponseEntity<ApiResponse<String>> addImagesToPropertiesWithoutImages() {
        try {
            int propertiesUpdated = propertyImageBulkService.addImagesToPropertiesWithoutImages();
            String message = "Successfully added images to " + propertiesUpdated + " properties";
            return ResponseEntity.ok(ApiResponse.success(message, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error adding images: " + e.getMessage()));
        }
    }

    /**
     * Add images to a specific property
     */
    @PostMapping("/{propertyId}/bulk-add-images")
    public ResponseEntity<ApiResponse<String>> addImagesToSpecificProperty(
            @PathVariable Long propertyId,
            @RequestParam(defaultValue = "3") int numberOfImages) {
        try {
            int imagesAdded = propertyImageBulkService.addImagesToSpecificProperty(propertyId, numberOfImages);
            String message = "Successfully added " + imagesAdded + " images to property " + propertyId;
            return ResponseEntity.ok(ApiResponse.success(message, null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error adding images: " + e.getMessage()));
        }
    }

    /**
     * Add images to ALL properties (replaces existing images)
     */
    @PostMapping("/bulk/add-images-to-all-properties")
    public ResponseEntity<ApiResponse<String>> addImagesToAllProperties() {
        try {
            int totalImagesAdded = propertyImageBulkService.addImagesToAllProperties();
            String message = "Successfully added " + totalImagesAdded + " images to all properties";
            return ResponseEntity.ok(ApiResponse.success(message, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error adding images: " + e.getMessage()));
        }
    }
}
