package com.sloanherrera.backend.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import com.sloanherrera.backend.dto.ExpenseRequest;
import com.sloanherrera.backend.dto.ExpenseResponse;
import com.sloanherrera.backend.services.ExpenseService;

import org.springframework.data.domain.Page;
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
    // an expense for a specified user. User ID as requested parameter is temporary
    // until Iteration 3, and only for testing in Iteration 1.
    @PostMapping("/expenses")
    public ExpenseResponse createExpense(@RequestBody ExpenseRequest req,
        @RequestParam UUID userId) {
            return expenseService.createExpense(userId, req);
    }

    // Represents GET HTTP method with the endpoint /expenses. Retrieves a
    // paginated list of expenses that belong to a specified user. User ID as 
    // requested parameter is temporary until Iteration 3, and only for testing in 
    // Iteration 1.
    @GetMapping("/expenses")
    public Page<ExpenseResponse> getExpenses(@RequestParam UUID userId, @RequestParam int page, 
        @RequestParam(required = false) Optional<String> category,
        @RequestParam(required = false) Optional<Integer> month, 
        @RequestParam(required = false) Optional<Integer> year) {
            return expenseService.readExpenses(userId, page, category, month, year);
    }

    // Represents PUT HTTP method with the endpoint /expenses/{expenseId}.
    // Updates an expense with new data for a specified user. User ID as requested 
    // parameter is temporary until Iteration 3, and only for testing in Iteration 1.
    @PutMapping("/expenses/{id}")
    public ExpenseResponse updateExpense(@RequestBody ExpenseRequest req,
        @RequestParam UUID userId, @PathVariable("id") UUID expenseId) {
            return expenseService.updateExpense(userId, expenseId, req);
    }

    // Represents DELETE HTTP method with the endpoint /expenses/{expenseId}.
    // Deletes an expense for a specified user. User ID as requested parameter is 
    // temporary until Iteration 3, and only for testing in Iteration 1.
    @DeleteMapping("/expenses/{id}")
    public void deleteExpense(@RequestParam UUID userId, 
        @PathVariable("id") UUID expenseId) {
            expenseService.deleteExpense(userId, expenseId);
    }
}
