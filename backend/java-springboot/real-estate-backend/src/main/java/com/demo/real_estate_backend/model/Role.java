package com.demo.real_estate_backend.model;

public enum Role {
	    GUEST,      // Can only view properties
	    USER,       // Can register, login, save favorites, search
	    SUBSCRIBER, // User + AI suggestions (future)
	    ADMIN       // Full access - manage properties, users, listings

}
