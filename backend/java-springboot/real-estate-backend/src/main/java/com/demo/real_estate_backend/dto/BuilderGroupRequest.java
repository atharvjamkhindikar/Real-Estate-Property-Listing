package com.demo.real_estate_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuilderGroupRequest {

    @NotBlank(message = "Builder group name is required")
    private String name;

    private String description;

    private Boolean active;
}
