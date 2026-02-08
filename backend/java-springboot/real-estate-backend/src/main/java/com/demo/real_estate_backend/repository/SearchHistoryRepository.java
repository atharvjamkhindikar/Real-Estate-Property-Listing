package com.demo.real_estate_backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.real_estate_backend.model.SearchHistory;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    
    List<SearchHistory> findByUserId(Long userId);
    
    List<SearchHistory> findByUserIdOrderBySearchedAtDesc(Long userId);
    
    List<SearchHistory> findByUserIdOrderBySearchedAtDesc(Long userId, Pageable pageable);
    
    List<SearchHistory> findBySearchedAtBetween(LocalDateTime start, LocalDateTime end);
    
    void deleteByUserId(Long userId);
    
    void deleteBySearchedAtBefore(LocalDateTime dateTime);
    
    @Query("SELECT sh.searchCity, COUNT(sh) FROM SearchHistory sh WHERE sh.searchCity IS NOT NULL GROUP BY sh.searchCity ORDER BY COUNT(sh) DESC")
    List<Object[]> findMostSearchedCities(Pageable pageable);
    
    @Query("SELECT sh.searchPropertyType, COUNT(sh) FROM SearchHistory sh WHERE sh.searchPropertyType IS NOT NULL GROUP BY sh.searchPropertyType ORDER BY COUNT(sh) DESC")
    List<Object[]> findMostSearchedPropertyTypes();
    
    @Query("SELECT sh FROM SearchHistory sh WHERE sh.user.id = :userId AND sh.searchCity = :city")
    List<SearchHistory> findByUserIdAndCity(@Param("userId") Long userId, @Param("city") String city);
    
    Long countByUserId(Long userId);
}
