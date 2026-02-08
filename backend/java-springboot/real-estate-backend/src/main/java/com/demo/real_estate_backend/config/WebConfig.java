package com.demo.real_estate_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for the Real Estate application.
 * Handles CORS for both local development and EC2 deployment.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Local development origins
                .allowedOrigins(
                    // EC2 production
//                    "http://ec2-13-220-57-64.compute-1.amazonaws.com"
                        "http://realestate-frontend.s3-website-us-east-1.amazonaws.com"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
