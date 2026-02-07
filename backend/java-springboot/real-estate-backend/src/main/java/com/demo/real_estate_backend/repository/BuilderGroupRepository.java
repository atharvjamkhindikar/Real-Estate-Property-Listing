package com.demo.real_estate_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.real_estate_backend.model.BuilderGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuilderGroupRepository extends JpaRepository<BuilderGroup, Long> {

    Optional<BuilderGroup> findByName(String name);

    Optional<BuilderGroup> findByNameIgnoreCase(String name);

    List<BuilderGroup> findByActive(Boolean active);

    List<BuilderGroup> findAllByOrderByNameAsc();
}

