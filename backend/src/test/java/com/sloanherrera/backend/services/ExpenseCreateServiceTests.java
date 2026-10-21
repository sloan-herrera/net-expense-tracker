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
 * Provides automated testing for expense creation in ExpenseService.java, the service 
 * layer class for expense management. NOTE: The project initially used manual testing 
 * to ensure functionality; automated testing was implemented retroactively.
 */

class ExpenseCreateServiceTests extends BaseExpenseServiceTests {
    // Test valid create expense request with expected response.
    @Test
    void createExpense_validRequest_returnsExpenseResponse() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        ExpenseRequest request = new ExpenseRequest("Groceries", 
            new BigDecimal("50.00"), "Food", date);

        // Mock existing user.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Create expense to return after save.
        Expense savedExpense = new Expense(
            "Groceries",
            new BigDecimal("50.00"),
            "Food",
            date,
            user
        );

        // Return the expense created by the service when it is saved.
        when(expenseRepository.save(any(Expense.class))).thenReturn(savedExpense);

        // Act by creating the expense.
        ExpenseResponse response = expenseService.createExpense(userId, request);

        // Assert returned expense data.
        assertEquals("Groceries", response.getName());
        assertEquals(new BigDecimal("50.00"), response.getAmount());
        assertEquals("Food", response.getCategory());
        assertEquals(date, response.getDate());

        // Capture the expense passed to the repository.
        verify(expenseRepository).save(expenseCaptor.capture());

        Expense capturedExpense = expenseCaptor.getValue();

        // Verify that the expense contains the expected data.
        assertEquals("Groceries", capturedExpense.getName());
        assertEquals(new BigDecimal("50.00"), capturedExpense.getAmount());
        assertEquals("Food", capturedExpense.getCategory());
        assertEquals(date, capturedExpense.getDate());
        assertEquals(user, capturedExpense.getUser());
    }

    // Test invalid create expense request with non-existent user with expected response.
    @Test
    void createExpense_userDoesNotExist_throwsNotFound() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        ExpenseRequest request = new ExpenseRequest("Groceries",
            new BigDecimal("50.00"), "Food", date);

        // Mock non-existent user.
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act by creating the expense and assert exception is thrown.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.createExpense(userId, request)
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid create expense request with null amount with expected response.
    @Test
    void createExpense_nullAmount_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        ExpenseRequest request = new ExpenseRequest("Groceries",
            null, "Food", date);

        // Mock existing user.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act by creating the expense and assert exception.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> expenseService.createExpense(userId, request));

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid create expense request with zero amount with expected response.
    @Test
    void createExpense_zeroAmount_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        ExpenseRequest request = new ExpenseRequest("Groceries",
            new BigDecimal("0.00"), "Food", date);

        // Mock existing user.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act by creating the expense and assert exception.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> expenseService.createExpense(userId, request));

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid create expense request with negative amount with expected response.
    @Test
    void createExpense_negativeAmount_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        ExpenseRequest request = new ExpenseRequest("Groceries",
            new BigDecimal("-50.00"), "Food", date);

        // Mock existing user.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act by creating the expense and assert exception.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> expenseService.createExpense(userId, request));

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid create expense request with amount more than two decimals with expected response.
    @Test
    void createExpense_amountMoreThanTwoDecimals_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        ExpenseRequest request = new ExpenseRequest("Groceries",
            new BigDecimal("50.001"), "Food", date);

        // Mock existing user.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act by creating the expense and assert exception.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> expenseService.createExpense(userId, request));

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid create expense request with null category with expected response.
    @Test
    void createExpense_nullCategory_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        ExpenseRequest request = new ExpenseRequest("Groceries",
            new BigDecimal("50.00"), null, date);

        // Mock existing user.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act by creating the expense and assert exception.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> expenseService.createExpense(userId, request));

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid create expense request with blank category with expected response.
    @Test
    void createExpense_blankCategory_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        ExpenseRequest request = new ExpenseRequest("Groceries",
            new BigDecimal("50.00"), "", date);

        // Mock existing user.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act by creating the expense and assert exception.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> expenseService.createExpense(userId, request));

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid create expense request with null date with expected response.
    @Test
    void createExpense_nullDate_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();

        User user = new User("test@test.com", "hashedPassword");

        ExpenseRequest request = new ExpenseRequest("Groceries",
            new BigDecimal("50.00"), "Food", null);

        // Mock existing user.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act by creating the expense and assert exception.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> expenseService.createExpense(userId, request));

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }

    // Test invalid create expense request with future date with expected response.
    @Test
    void createExpense_futureDate_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now().plusDays(1);

        User user = new User("test@test.com", "hashedPassword");

        ExpenseRequest request = new ExpenseRequest("Groceries",
            new BigDecimal("50.00"), "Food", date);

        // Mock existing user.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act by creating the expense and assert exception.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> expenseService.createExpense(userId, request));

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that an expense was not saved.
        verify(expenseRepository, never()).save(any(Expense.class));
    }
}
