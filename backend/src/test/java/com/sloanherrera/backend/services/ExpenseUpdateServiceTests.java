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

import com.sloanherrera.backend.dto.ExpenseRequest;
import com.sloanherrera.backend.dto.ExpenseResponse;
import com.sloanherrera.backend.models.Expense;
import com.sloanherrera.backend.models.User;

/**
 * Provides automated testing for updating expenses in ExpenseService.java, the service 
 * layer class for expense management. NOTE: The project initially used manual testing 
 * to ensure functionality; automated testing was implemented retroactively.
 */

class ExpenseUpdateServiceTests extends BaseExpenseServiceTests {
    // Test valid expense update request.
    @Test
    void updateExpense_validRequest_returnsUpdatedExpense() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        UUID expenseId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        Expense existingExpense = new Expense(
            "Groceries",
            new BigDecimal("50.00"),
            "Food",
            date.minusDays(1),
            user
        );

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("35.00"),
            "Transportation",
            date
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Mock saving the updated expense.
        when(expenseRepository.save(any(Expense.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act by updating the expense.
        ExpenseResponse response = expenseService.updateExpense(
            userId, expenseId, request
        );

        // Assert returned expense data.
        assertEquals("Gas", response.getName());
        assertEquals(new BigDecimal("35.00"), response.getAmount());
        assertEquals("Transportation", response.getCategory());
        assertEquals(date, response.getDate());

        // Capture the expense passed to the repository.
        verify(expenseRepository).save(expenseCaptor.capture());

        Expense capturedExpense = expenseCaptor.getValue();

        // Verify that the existing expense contains the updated data.
        assertEquals("Gas", capturedExpense.getName());
        assertEquals(new BigDecimal("35.00"), capturedExpense.getAmount());
        assertEquals("Transportation", capturedExpense.getCategory());
        assertEquals(date, capturedExpense.getDate());

        // Verify that the expense still belongs to the original user.
        assertEquals(user, capturedExpense.getUser());

        // Verify that the correct expense was retrieved.
        verify(expenseRepository).findByUserUserIdAndExpenseId(userId, expenseId);
    }

    // Test invalid expense update request with non-existent expense.
    @Test
    void updateExpense_nonexistentExpense_throwsNotFound() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        UUID expenseId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("35.00"),
            "Transportation",
            date
        );

        // Mock non-existent expense.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.empty());

        // Act by updating a non-existent expense and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid expense update request with wrong user.
    @Test
    void updateExpense_wrongUser_throwsNotFound() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        UUID expenseId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("35.00"),
            "Transportation",
            date
        );

        // Mock an expense that is not accessible to the requesting user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.empty());

        // Act by attempting to update an inaccessible expense and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify that the lookup is scoped to the requesting user.
        verify(expenseRepository)
            .findByUserUserIdAndExpenseId(userId, expenseId);

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid expense update request with null amount.
    @Test
    void updateExpense_nullAmount_throwsBadRequest() {
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

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            null,
            "Transportation",
            date
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Act by updating an expense with a null amount and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid expense update request with zero amount.
    @Test
    void updateExpense_zeroAmount_throwsBadRequest() {
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

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("0.00"),
            "Transportation",
            date
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Act by updating an expense with a zero amount and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid expense update request with negative amount.
    @Test
    void updateExpense_negativeAmount_throwsBadRequest() {
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

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("-35.00"),
            "Transportation",
            date
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Act by updating an expense with a negative amount and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid expense update request with amount more than two decimals.
    @Test
    void updateExpense_amountMoreThanTwoDecimals_throwsBadRequest() {
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

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("35.001"),
            "Transportation",
            date
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Act by updating an expense with an amount more than two decimals and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid expense update request with null category.
    @Test
    void updateExpense_nullCategory_throwsBadRequest() {
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

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("35.00"),
            null,
            date
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Act by updating an expense with a null category and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid expense update request with blank category.
    @Test
    void updateExpense_blankCategory_throwsBadRequest() {
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

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("35.00"),
            "",
            date
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Act by updating an expense with a blank category and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid expense update request with null date.
    @Test
    void updateExpense_nullDate_throwsBadRequest() {
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

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("35.00"),
            "Transportation",
            null
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Act by updating an expense with a null date and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid expense update request with future date.
    @Test
    void updateExpense_futureDate_throwsBadRequest() {
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

        ExpenseRequest request = new ExpenseRequest(
            "Gas",
            new BigDecimal("35.00"),
            "Transportation",
            LocalDate.now().plusDays(1)
        );

        // Mock existing expense belonging to the user.
        when(expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId))
            .thenReturn(Optional.of(existingExpense));

        // Act by updating an expense with a future date and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.updateExpense(userId, expenseId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }
}
