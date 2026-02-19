package com.sloanherrera.backend.dto;

import java.util.UUID;

/**
 * This is a UserResponse DTO (Data Transfer Object) to ensure that entities are
 * not directly accessed. It also makes communication between frontend and 
 * backend easier. Response is server to client.
 */
public class UserResponse {
    // Password is not included in response for security purposes.
    private UUID userId;
    private String email;

    // Empty constructor required by Spring Boot.
    public UserResponse() {}

    public UserResponse(UUID userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    // Getters and setters.

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
