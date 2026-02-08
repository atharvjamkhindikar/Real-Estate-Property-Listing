package com.demo.real_estate_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")

                // Allow local React app (DEV)
                // Allow deployed frontend (PROD)
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://realestate-frontend.s3-website-us-east-1.amazonaws.com"
                )

                // Explicitly allow all HTTP methods you use
                .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                )

                // Allow all headers (Authorization, Content-Type, etc.)
                .allowedHeaders("*")

                // Allow cookies / Authorization headers
                .allowCredentials(true)

                // Cache preflight response (performance improvement)
                .maxAge(3600);
    }
}
