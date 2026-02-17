package com.sloanherrera.backend.models;

import java.util.UUID;

import jakarta.persistence.*;

/**
 * An entity that represents an authenticated user of the application in the
 * MySQL relational database.
 */
@Entity
@Table(name="users")
public class User {
    // ID value is required to be randomly generated, so UUID used to ensure
    // unique values, as opposed to sequential ID generation.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, updatable = false) // Column name in MySQL database.
    private UUID userId;

    // Email is used as opposed to a username to enforce standardization. Must be unique.
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Password is encrypted before it is stored.
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    // Empty constructor, required by Spring Boot JPA.
    public User() {} 

    // Overloaded constructor to make setting values easier.
    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters and setters

    // No setter for ID, as it is automatically generated.
    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
