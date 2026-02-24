package com.sloanherrera.backend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sloanherrera.backend.dto.UserRequest;
import com.sloanherrera.backend.dto.UserResponse;
import com.sloanherrera.backend.services.UserService;

/**
 * A controller that enables maps user authentication to API endpoints. For
 * Iteration 1, authentication is disabled until Iteration 3, so only the 
 * ability to create a user (register) is currently present.
 */
@RestController
public class AuthController {
    // Declared as final so that the variables are never reassigned.
    private final UserService userService;

    // Constructor assigns expense service.
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Represents POST HTTP for user registration with the endpoint. Creates a 
    // user.
    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest req) {
        return userService.createUser(req);
    }
}
