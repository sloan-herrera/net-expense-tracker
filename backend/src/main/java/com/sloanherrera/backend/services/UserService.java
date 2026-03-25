package com.sloanherrera.backend.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sloanherrera.backend.dto.UserRequest;
import com.sloanherrera.backend.dto.UserResponse;
import com.sloanherrera.backend.models.User;
import com.sloanherrera.backend.repositories.UserRepository;

/**
 * A service that defines business logic for users. Includes user account
 * creation and login user functionality.
 * Note: Logout functionality is implemented in AuthController because
 * it only invalidates the session, which is handled by controller.
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
        // Data validation.
        validateEmail(req.getEmail());
        validatePassword(req.getPassword());

        // Convert email to lowercase, as specified in updated requirements
        // document. Provides a universal format. Also, trim spaces for
        // edge cases where user types spaces before or after email.
        String lowerCaseEmail = req.getEmail().toLowerCase().trim();

        // Check for email duplicates. If there are duplicates, throw HTTP
        // error 409.
        if (userRepository.existsByEmail(lowerCaseEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                "User already exists.");
        }

        // Hash the password before storing for security purposes.
        String hashedPassword = passwordEncoder.encode(req.getPassword());

        // Created user entity based on passed request.
        User user = new User(lowerCaseEmail, hashedPassword);

        // Save the user to the database.
        User savedUser = userRepository.save(user);

        // Return the saved entity through the response.
        return mapEntityToResponse(savedUser);
    }

    // This method is called when a user logs into the application.
    public UserResponse loginUser(UserRequest req) {
        // Data validation.
        validateEmail(req.getEmail());
        validatePassword(req.getPassword());

        // Convert email to lowercase to match registration format.
        String lowerCaseEmail = req.getEmail().toLowerCase().trim();

        // Find user by email for login. If the email is incorrect or does not
        // exist by email, throw HTTP error 401 to prevent login.
        User user = userRepository.findByEmail(lowerCaseEmail)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                "Invalid email or password."));
        
        // Check if input password matches the BCrypt password hash in database. If
        // it does not match, throw HTTP error 401 to prevent login.
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, 
                "Invalid email or password.");
        }

        // Return authenticated user through response.
        return mapEntityToResponse(user);
    }

    // Required to map the User entity to the UserResponse DTO.
    private UserResponse mapEntityToResponse(User user) {
        return new UserResponse(user.getUserId(), user.getEmail());
    }

    // Validate email field for account creation or login.
    private void validateEmail(String email) {
        // Check if email is null. If it is, throw HTTP error 400.
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email must" +
                " not be null or blank.");
        }
    }

    // Validate password field for account creation or login.
    private void validatePassword(String password) {
        // Check if password is null. If it is, throw HTTP error 400.
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password" +
                " must not be null or blank.");
        }

        // Check if password is less than 12 characters long. If it is, throw HTTP 
        // error 400.
        if (password.length() < 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password" +
                " must be at least 12 characters long.");
        }
    }
}
