package com.sloanherrera.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.sloanherrera.backend.models.Expense;
import com.sloanherrera.backend.models.User;

/**
 * Provides automated testing for expense deletion in ExpenseService.java, the service 
 * layer class for expense management. NOTE: The project initially used manual testing 
 * to ensure functionality; automated testing was implemented retroactively.
 */

class ExpenseDeleteServiceTests extends BaseExpenseServiceTests {
    // Test successful deletion of an existing expense.
    @Test
    void deleteExpense_existingExpense_deletesExpense() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        UUID expenseId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        Expense existingExpense = new Expense(
            "Groceries",
            new BigDecimal("50.00"),
            "Food",
            date,
            user
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Act by deleting an existing expense.
        expenseService.deleteExpense(userId, expenseId);

        // Verify that the expense was deleted.
        verify(expenseRepository).delete(existingExpense);
    }

    // Test deletion request for a non-existent expense.
    @Test
    void deleteExpense_nonexistentExpense_throwsNotFound() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        UUID expenseId = UUID.randomUUID();

        // Mock nonexistent expense.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.empty());

        // Act by deleting a non-existent expense and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.deleteExpense(userId, expenseId)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify that no expense was deleted.
        verify(expenseRepository, never()).delete(any(Expense.class));
    }

    // Test deletion request for an expense not belonging to the user.
    @Test 
    void deleteExpense_wrongUser_throwsNotFound() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        UUID expenseId = UUID.randomUUID();

        // Mock expense not found under the supplied user ID.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.empty());

        // Act by deleting an expense belonging to another user and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.deleteExpense(userId, expenseId)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify that no expense was deleted.
        verify(expenseRepository, never()).delete(any(Expense.class));
    }
}
