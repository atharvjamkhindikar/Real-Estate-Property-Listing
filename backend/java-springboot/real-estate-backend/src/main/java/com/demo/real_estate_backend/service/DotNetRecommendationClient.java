package com.demo.real_estate_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.demo.real_estate_backend.model.Property;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DotNetRecommendationClient {
	
	private static final Logger logger = LoggerFactory.getLogger(DotNetRecommendationClient.class);
    private static final String DOT_NET_API_URL = "http://localhost:5000/api/recommend";

    @Autowired
    private RestTemplate restTemplate;

    public List<Property> getRecommendations(List<Property> properties, String location, double budget) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("properties", properties);
            body.put("location", location);
            body.put("budget", budget);

            logger.info("Calling .NET recommendation service for location: {} with budget: {}", location, budget);

            ResponseEntity<Property[]> response =
                    restTemplate.postForEntity(DOT_NET_API_URL, body, Property[].class);

            if (response.getBody() != null) {
                logger.info("Successfully retrieved {} recommendations", response.getBody().length);
                return Arrays.asList(response.getBody());
            }
            logger.warn("Empty response from .NET recommendation service");
        } catch (RestClientException e) {
            logger.error("Error calling .NET recommendation service: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error in getRecommendations: {}", e.getMessage(), e);
        }
        return List.of();
    }

}
