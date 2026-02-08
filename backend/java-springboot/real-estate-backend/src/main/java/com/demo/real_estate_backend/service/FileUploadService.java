package com.demo.real_estate_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload.dir:uploads/images}")
    private String uploadDir;

    @Value("${file.upload.max-size:5242880}") // 5MB default
    private long maxFileSize;

    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif", "webp"};
    private static final String[] ALLOWED_MIME_TYPES = {
        "image/jpeg",
        "image/png",
        "image/gif",
        "image/webp"
    };

    /**
     * Upload an image file and return the file path/URL
     * @param file MultipartFile to upload
     * @return String path or URL to the uploaded file
     * @throws IOException if upload fails
     */
    public String uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Validate file size
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds maximum allowed size of " + (maxFileSize / 1024 / 1024) + "MB");
        }

        // Validate MIME type
        String contentType = file.getContentType();
        if (contentType == null || !isAllowedMimeType(contentType)) {
            throw new IllegalArgumentException("File type not allowed. Allowed types: JPEG, PNG, GIF, WebP");
        }

        // Validate file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !isAllowedExtension(originalFilename)) {
            throw new IllegalArgumentException("File extension not allowed. Allowed: jpg, jpeg, png, gif, webp");
        }

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);

        // Generate unique filename
        String uniqueFilename = generateUniqueFilename(originalFilename);
        Path filePath = uploadPath.resolve(uniqueFilename);

        // Save file
        Files.write(filePath, file.getBytes());

        // Return file path (can be adjusted to return URL instead)
        return uploadDir + "/" + uniqueFilename;
    }

    /**
     * Delete an uploaded image file
     * @param filePath Path to the file to delete
     */
    public void deleteImage(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            // Log but don't fail if deletion fails
            System.err.println("Failed to delete file: " + filePath + ", Error: " + e.getMessage());
        }
    }

    /**
     * Check if file extension is allowed
     */
    private boolean isAllowedExtension(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        for (String allowed : ALLOWED_EXTENSIONS) {
            if (allowed.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if MIME type is allowed
     */
    private boolean isAllowedMimeType(String mimeType) {
        for (String allowed : ALLOWED_MIME_TYPES) {
            if (allowed.equals(mimeType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generate a unique filename to avoid conflicts
     */
    private String generateUniqueFilename(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueName = UUID.randomUUID() + extension;
        return System.currentTimeMillis() + "_" + uniqueName;
    }
}
