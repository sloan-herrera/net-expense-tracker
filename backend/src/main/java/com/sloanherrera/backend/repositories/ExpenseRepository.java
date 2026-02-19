package com.sloanherrera.backend.repositories;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import com.sloanherrera.backend.models.Expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on Expense entities.
 * JpaRepository enables searching the database without needing MySQL queries.
 */
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    // Retrieve a paginated list of expenses by user ID. Used for GET
    // HTTP method.
    Page<Expense> findByUserUserId(UUID userId, Pageable page);

    // Retrieve a single expense by expense ID and user ID. Used for UPDATE
    // and DELETE HTTP methods.
    Optional<Expense> findByUserUserIdAndExpenseId(UUID userId, UUID expenseId);

    // Retrieve a paginated list of expenses by user ID and expense 
    // category. Used for filtering expenses by category.
    Page<Expense> findByUserUserIdAndCategory(UUID userId, String category, Pageable page);

    // Retrieve a paginated list of expenses by user ID and month.
    // Used for filtering by month. LocalDate does not support month queries,
    // so start of month to end of month must be defined.
    Page<Expense> findByUserUserIdAndDateBetween(UUID userId, 
        LocalDate startDate, LocalDate endDate, Pageable page);

    // Retrieve a paginated list of expenses by user ID, expense category, and
    // month. Used for filtering by both expense category and month.
    Page<Expense> findByUserUserIdAndCategoryAndDateBetween(UUID userId, String category, 
        LocalDate startDate, LocalDate endDate, Pageable page);
}
