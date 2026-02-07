package com.demo.real_estate_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.real_estate_backend.model.User;
import com.demo.real_estate_backend.model.UserType;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByUserType(UserType userType);
    
    List<User> findByActiveTrue();
    
    List<User> findByUserTypeAndActiveTrue(UserType userType);
}
