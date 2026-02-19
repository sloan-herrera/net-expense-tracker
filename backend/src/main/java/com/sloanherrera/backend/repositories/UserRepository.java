package com.sloanherrera.backend.repositories;

import java.util.Optional;
import java.util.UUID;

import com.sloanherrera.backend.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on User entities.
 * JpaRepository enables searching the database without needing MySQL queries.
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    // Search database for User entity with matching email for authentication
    // purposes.
    Optional<User> findByEmail(String email);
}
