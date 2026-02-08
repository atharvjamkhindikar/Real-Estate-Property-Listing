package com.demo.real_estate_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.real_estate_backend.dto.ApiResponse;
import com.demo.real_estate_backend.model.SearchHistory;
import com.demo.real_estate_backend.service.SearchHistoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search-history")
public class SearchHistoryController {
    
    @Autowired
    private SearchHistoryService searchHistoryService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<SearchHistory>>> getUserSearchHistory(@PathVariable Long userId) {
        List<SearchHistory> history = searchHistoryService.getUserSearchHistory(userId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
    
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<ApiResponse<List<SearchHistory>>> getUserRecentSearches(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        List<SearchHistory> history = searchHistoryService.getUserRecentSearches(userId, limit);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
    
    @DeleteMapping("/{historyId}")
    public ResponseEntity<ApiResponse<Void>> deleteSearchHistory(@PathVariable Long historyId) {
        searchHistoryService.deleteSearchHistory(historyId);
        return ResponseEntity.ok(ApiResponse.success("Search history deleted", null));
    }
    
    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<ApiResponse<Void>> clearUserSearchHistory(@PathVariable Long userId) {
        searchHistoryService.clearUserSearchHistory(userId);
        return ResponseEntity.ok(ApiResponse.success("Search history cleared", null));
    }
    
    @GetMapping("/analytics/cities")
    public ResponseEntity<ApiResponse<List<Map.Entry<String, Long>>>> getMostSearchedCities(
            @RequestParam(defaultValue = "10") int limit) {
        List<Map.Entry<String, Long>> cities = searchHistoryService.getMostSearchedCities(limit);
        return ResponseEntity.ok(ApiResponse.success(cities));
    }
    
    @GetMapping("/analytics/property-types")
    public ResponseEntity<ApiResponse<List<Map.Entry<String, Long>>>> getMostSearchedPropertyTypes() {
        List<Map.Entry<String, Long>> propertyTypes = searchHistoryService.getMostSearchedPropertyTypes();
        return ResponseEntity.ok(ApiResponse.success(propertyTypes));
    }
    
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUserSearchCount(@PathVariable Long userId) {
        Long count = searchHistoryService.getUserSearchCount(userId);
        return ResponseEntity.ok(ApiResponse.success(Map.of("searchCount", count)));
    }
}
