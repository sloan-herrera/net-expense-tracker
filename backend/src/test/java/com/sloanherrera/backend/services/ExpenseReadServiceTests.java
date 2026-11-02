package com.sloanherrera.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.sloanherrera.backend.dto.ExpenseResponse;
import com.sloanherrera.backend.models.Expense;
import com.sloanherrera.backend.models.User;

/**
 * Provides automated testing for reading expenses in ExpenseService.java, the service 
 * layer class for expense management. NOTE: The project initially used manual testing 
 * to ensure functionality; automated testing was implemented retroactively.
 */

class ExpenseReadServiceTests extends BaseExpenseServiceTests {
    // Test valid expense retrieval without filters, including pagination.
    @Test
    void readExpenses_noFilters_returnsPaginatedExpenses() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        Expense expense = new Expense(
            "Groceries",
            new BigDecimal("50.00"),
            "Food",
            date,
            user
        );

        // Configure pagination: page 0, 10 expenses per page, newest first.
        Pageable pageable = PageRequest.of(
            0, 10, Sort.by("date").descending()
        );

        // Simulate a page returned by the repository.
        Page<Expense> expensePage = new PageImpl<>(
            List.of(expense),
            pageable,
            1
        );

        // Mock existing user and repository response.
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        when(expenseRepository.findByUserUserId(userId, pageable))
            .thenReturn(expensePage);

        // Act by retrieving expenses without filters.
        Page<ExpenseResponse> response = expenseService.readExpenses(
            userId,
            0,
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
        );

        // Assert pagination metadata.
        assertEquals(0, response.getNumber());
        assertEquals(10, response.getSize());
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());

        // Assert returned expense data.
        assertEquals(1, response.getContent().size());
        assertEquals("Groceries", response.getContent().get(0).getName());
        assertEquals(new BigDecimal("50.00"), response.getContent().get(0).getAmount());
        assertEquals("Food", response.getContent().get(0).getCategory());
        assertEquals(date, response.getContent().get(0).getDate());

        // Verify correct repository method and pagination.
        verify(expenseRepository).findByUserUserId(userId, pageable);
    }

    // Test valid expense retrieval filtered by category.
    @Test
    void readExpenses_categoryFilter_returnsMatchingExpenses() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        User user = new User("test@test.com", "hashedPassword");

        Expense expense = new Expense(
            "Groceries",
            new BigDecimal("50.00"),
            "Food",
            date,
            user
        );

        // Configure pagination.
        Pageable pageable = PageRequest.of(
            0, 10, Sort.by("date").descending()
        );

        // Simulate a page containing expenses in the Food category.
        Page<Expense> expensePage = new PageImpl<>(
            List.of(expense),
            pageable,
            1
        );

        // Mock existing user.
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        // Mock repository response for category filtering.
        when(expenseRepository.findByUserUserIdAndCategory(
            userId, "Food", pageable
        )).thenReturn(expensePage);

        // Act by retrieving expenses filtered by category.
        Page<ExpenseResponse> response = expenseService.readExpenses(
            userId,
            0,
            Optional.of("Food"),
            Optional.empty(),
            Optional.empty()
        );

        // Assert returned expense data.
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        assertEquals("Groceries", response.getContent().get(0).getName());
        assertEquals("Food", response.getContent().get(0).getCategory());

        // Verify the correct repository method and arguments.
        verify(expenseRepository).findByUserUserIdAndCategory(
            userId, "Food", pageable
        );
    }

    // Test valid expense retrieval filtered by month and year.
    @Test
    void readExpenses_monthYearFilter_returnsMatchingExpenses() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();

        LocalDate date = LocalDate.of(2026, 2, 10);
        LocalDate startDate = LocalDate.of(2026, 2, 1);
        LocalDate endDate = LocalDate.of(2026, 2, 28);

        User user = new User("test@test.com", "hashedPassword");

        Expense expense = new Expense(
            "Groceries",
            new BigDecimal("50.00"),
            "Food",
            date,
            user
        );

        // Configure pagination.
        Pageable pageable = PageRequest.of(
            0, 10, Sort.by("date").descending()
        );

        // Simulate a page containing expenses from February 2026.
        Page<Expense> expensePage = new PageImpl<>(
            List.of(expense),
            pageable,
            1
        );

        // Mock existing user.
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        // Mock repository response for month/year filtering.
        when(expenseRepository.findByUserUserIdAndDateBetween(
            userId, startDate, endDate, pageable
        )).thenReturn(expensePage);

        // Act by retrieving expenses filtered by month and year.
        Page<ExpenseResponse> response = expenseService.readExpenses(
            userId,
            0,
            Optional.empty(),
            Optional.of(2),
            Optional.of(2026)
        );

        // Assert returned expense data.
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        assertEquals("Groceries", response.getContent().get(0).getName());
        assertEquals(date, response.getContent().get(0).getDate());

        // Verify the correct repository method and date boundaries.
        verify(expenseRepository).findByUserUserIdAndDateBetween(
            userId, startDate, endDate, pageable
        );
    }

    // Test valid expense retrieval filtered by category, month, and year.
    @Test
    void readExpenses_categoryAndMonthYearFilter_returnsMatchingExpenses() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();

        LocalDate date = LocalDate.of(2026, 3, 15);
        LocalDate startDate = LocalDate.of(2026, 3, 1);
        LocalDate endDate = LocalDate.of(2026, 3, 31);

        User user = new User("test@test.com", "hashedPassword");

        Expense expense = new Expense(
            "Groceries",
            new BigDecimal("50.00"),
            "Food",
            date,
            user
        );

        // Configure pagination.
        Pageable pageable = PageRequest.of(
            0, 10, Sort.by("date").descending()
        );

        // Simulate a page containing Food expenses from March 2026.
        Page<Expense> expensePage = new PageImpl<>(
            List.of(expense),
            pageable,
            1
        );

        // Mock existing user.
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        // Mock repository response for combined filtering.
        when(expenseRepository.findByUserUserIdAndCategoryAndDateBetween(
            userId, "Food", startDate, endDate, pageable
        )).thenReturn(expensePage);

        // Act by retrieving expenses with both filters.
        Page<ExpenseResponse> response = expenseService.readExpenses(
            userId,
            0,
            Optional.of("Food"),
            Optional.of(3),
            Optional.of(2026)
        );

        // Assert returned expense data.
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        assertEquals("Groceries", response.getContent().get(0).getName());
        assertEquals("Food", response.getContent().get(0).getCategory());
        assertEquals(date, response.getContent().get(0).getDate());

        // Verify correct repository method and arguments.
        verify(expenseRepository).findByUserUserIdAndCategoryAndDateBetween(
            userId, "Food", startDate, endDate, pageable
        );
    }

    // Test invalid expense retrieval with a negative page number.
    @Test
    void readExpenses_negativePage_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();

        User user = new User("test@test.com", "hashedPassword");

        // Mock existing user.
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        // Act by retrieving expenses with a negative page number and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.readExpenses(
                userId,
                -1,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
            )
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that the expense repository was not called.
        verifyNoInteractions(expenseRepository);
    }

    // Test invalid expense retrieval with a month below valid range.
    @Test
    void readExpenses_monthBelowOne_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();

        User user = new User("test@test.com", "hashedPassword");

        // Mock existing user.
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        // Act by retrieving expenses with an invalid month and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.readExpenses(
                userId,
                0,
                Optional.empty(),
                Optional.of(0),
                Optional.of(2026)
            )
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that the expense repository was not called.
        verifyNoInteractions(expenseRepository);
    }

    // Test invalid expense retrieval with a month above valid range.
    @Test
    void readExpenses_monthAboveTwelve_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();

        User user = new User("test@test.com", "hashedPassword");

        // Mock existing user.
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        // Act by retrieving expenses with an invalid month and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.readExpenses(
                userId,
                0,
                Optional.empty(),
                Optional.of(13),
                Optional.of(2026)
            )
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that the expense repository was not called.
        verifyNoInteractions(expenseRepository);
    }

    // Test invalid expense retrieval with a month without a year.
    @Test
    void readExpenses_monthWithoutYear_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();

        User user = new User("test@test.com", "hashedPassword");

        // Mock existing user.
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        // Act by retrieving expenses with a month but no year and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.readExpenses(
                userId,
                0,
                Optional.empty(),
                Optional.of(2),
                Optional.empty()
            )
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that the expense repository was not called.
        verifyNoInteractions(expenseRepository);
    }

    // Test invalid expense retrieval with a year without a month.
    @Test
    void readExpenses_yearWithoutMonth_throwsBadRequest() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();

        User user = new User("test@test.com", "hashedPassword");

        // Mock existing user.
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

        // Act by retrieving expenses with a year but no month and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.readExpenses(
                userId,
                0,
                Optional.empty(),
                Optional.empty(),
                Optional.of(2026)
            )
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

        // Verify that the expense repository was not called.
        verifyNoInteractions(expenseRepository);
    }

    // Test invalid expense retrieval with a non-existent user.
    @Test
    void readExpenses_nonexistentUser_throwsNotFound() {
        // Arrange test data.
        UUID userId = UUID.randomUUID();
        
        // Mock non-existent user.
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act by retrieving expenses with non-existent user and assert exception.
        ResponseStatusException exception = assertThrows(
            ResponseStatusException.class,
            () -> expenseService.readExpenses(
                userId,
                0,
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
            )
        );

        // Assert returned HTTP status.
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verify that the expense repository was not called.
        verifyNoInteractions(expenseRepository);
    }
}
