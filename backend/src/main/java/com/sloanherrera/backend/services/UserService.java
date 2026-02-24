package com.sloanherrera.backend.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sloanherrera.backend.dto.UserRequest;
import com.sloanherrera.backend.dto.UserResponse;
import com.sloanherrera.backend.models.User;
import com.sloanherrera.backend.repositories.UserRepository;

/**
 * UNFINISHED
 * A service that defines business logic for users.
 * NOTE: In Agile Iteration 1, it is only necessary for the user to be created.
 * Further authentication and logout functionality has been pushed to iteration
 * 3, along with security and connecting the backend and frontend.
 */
@Service
public class UserService {
    // Declared as final so that the variables are never reassigned.
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // For hashing.

    // Constructor assigns user repository and BCrypt password encoder.
    public UserService(UserRepository userRepository,
        BCryptPasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
    }

    // This method is called when a user completes the registration process.
    public UserResponse createUser(UserRequest req) {
        // Convert email to lowercase, as specified in updated requirements
        // document. Provides a universal format.
        String lowerCaseEmail = req.getEmail().toLowerCase();
        // Hash the password before storing for security purposes.
        String hashedPassword = passwordEncoder.encode(req.getPassword());

        // Created user entity based on passed request.
        User user = new User(lowerCaseEmail, hashedPassword);

        // Save the user to the database.
        userRepository.save(user);

        // Return the saved entity through the response.
        return mapEntityToResponse(user);
    }

    // Required to map the User entity to the UserResponse DTO.
    private UserResponse mapEntityToResponse(User user) {
        return new UserResponse(user.getUserId(), user.getEmail());
    }
}
