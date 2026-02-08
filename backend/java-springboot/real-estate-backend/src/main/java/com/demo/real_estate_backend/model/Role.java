package com.demo.real_estate_backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
	USER,   // Matches frontend "User"
    AGENT,  // Matches frontend "Agent"
    ADMIN;  // Matches frontend "Admin"
    
}
