package com.demo.real_estate_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyImageRequest {

    private String imageUrl;  // Image URL can be from file upload or external source

    private String caption;

    private Boolean isPrimary;

    private Integer displayOrder;
}
