package com.sloanherrera.backend.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sloanherrera.backend.dto.ExpenseRequest;
import com.sloanherrera.backend.dto.ExpenseResponse;
import com.sloanherrera.backend.services.ExpenseService;

import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A controller that maps CRUD logic to expense API endpoints.
 */
@RestController
public class ExpenseController {
    // Declared as final so that the variables are never reassigned.
    private final ExpenseService expenseService;

    // Constructor assigns expense service.
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Represents POST HTTP method with the endpoint /expenses. Creates 
    // an expense for the authenticated user. 
    @PostMapping("/expenses")
    public ExpenseResponse createExpense(HttpSession session,
        @RequestBody ExpenseRequest req) {
            // Get user ID from session data.
            UUID userId = getUserIdFromSession(session);

            // Create expense.
            return expenseService.createExpense(userId, req);
    }

    // Represents GET HTTP method with the endpoint /expenses. Retrieves a
    // paginated list of expenses that belong to the authenticated user.
    @GetMapping("/expenses")
    public Page<ExpenseResponse> getExpenses(HttpSession session,
        @RequestParam int page, 
        @RequestParam(required = false) Optional<String> category,
        @RequestParam(required = false) Optional<Integer> month, 
        @RequestParam(required = false) Optional<Integer> year) {
            // Get user ID from session data.
            UUID userId = getUserIdFromSession(session);

            // Get paginated list of expenses.
            return expenseService.readExpenses(userId, page, category, month, year);
    }

    // Represents PUT HTTP method with the endpoint /expenses/{expenseId}.
    // Updates an expense with new data for the authenticated user.
    @PutMapping("/expenses/{id}")
    public ExpenseResponse updateExpense(HttpSession session,
        @RequestBody ExpenseRequest req, @PathVariable("id") UUID expenseId) {
            // Get user ID from session data.
            UUID userId = getUserIdFromSession(session);

            // Update expense.
            return expenseService.updateExpense(userId, expenseId, req);
    }

    // Represents DELETE HTTP method with the endpoint /expenses/{expenseId}.
    // Deletes an expense for the authenticated user.
    @DeleteMapping("/expenses/{id}")
    public void deleteExpense(HttpSession session, 
        @PathVariable("id") UUID expenseId) {
            // Get user ID from session data.
            UUID userId = getUserIdFromSession(session);

            // Delete expense.
            expenseService.deleteExpense(userId, expenseId);
    }

    // Gets authenticated user ID from session.
    private UUID getUserIdFromSession(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");

        // Checks if user is authenticated with valid session. If not, throw HTTP error 401.
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not logged" +
                " in.");
        }

        return userId;
    }
}
