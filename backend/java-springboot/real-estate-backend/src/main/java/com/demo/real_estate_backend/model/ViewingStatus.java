package com.demo.real_estate_backend.model;

public enum ViewingStatus {
    PENDING,      // Viewing request submitted, awaiting confirmation
    CONFIRMED,    // Viewing confirmed by agent/owner
    REJECTED,     // Viewing request rejected
    COMPLETED,    // Viewing was completed
    CANCELLED     // Viewing was cancelled by user
}
