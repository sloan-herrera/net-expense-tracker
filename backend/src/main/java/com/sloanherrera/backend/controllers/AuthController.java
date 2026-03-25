package com.sloanherrera.backend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sloanherrera.backend.dto.UserRequest;
import com.sloanherrera.backend.dto.UserResponse;
import com.sloanherrera.backend.services.UserService;

import jakarta.servlet.http.HttpSession;

/**
 * A controller that maps user authentication logic to API endpoints.
 */
@RestController
public class AuthController {
    // Declared as final so that the variables are never reassigned.
    private final UserService userService;

    // Constructor assigns user service.
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Represents POST HTTP method for user registration. Creates a 
    // user.
    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest req) {
        return userService.createUser(req);
    }

    // Represents POST HTTP method for user login. Authenticates
    // user and stores user data in session.
    @PostMapping("/login")
    public UserResponse loginUser(@RequestBody UserRequest req, HttpSession 
        session) {
            // UserResponse DTO to be returned, calling UserService loginUser() method.
            UserResponse user = userService.loginUser(req);

            // Store authenticated user data in session.
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("userEmail", user.getEmail());

            return user;
    }

    // Represents POST HTTP for user logout. Invalidates the session,
    // which will force the user back to the Sign-In page.
    @PostMapping("/logout")
    public void logoutUser(HttpSession session) {
        session.invalidate();
    }
}
