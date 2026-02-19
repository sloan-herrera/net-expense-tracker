package com.sloanherrera.backend.dto;

/**
 * This is a UserRequest DTO (Data Transfer Object) to ensure that entities are
 * not directly accessed. It also makes communication between frontend and 
 * backend easier. Request is client to server.
 */
public class UserRequest {
    // User ID is not required in request because IDs are assigned by JPA.
    private String email;
    private String password;

    // Empty constructor required by Spring Boot.
    public UserRequest() {}

    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters.

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
