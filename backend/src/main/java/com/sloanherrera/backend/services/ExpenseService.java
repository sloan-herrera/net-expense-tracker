package com.sloanherrera.backend.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.sloanherrera.backend.dto.ExpenseRequest;
import com.sloanherrera.backend.dto.ExpenseResponse;
import com.sloanherrera.backend.models.Expense;
import com.sloanherrera.backend.models.User;
import com.sloanherrera.backend.repositories.ExpenseRepository;
import com.sloanherrera.backend.repositories.UserRepository;

/**
 * A service that defines the business logic for expenses. CRUD
 * operations on expenses are defined here.
 */
@Service
public class ExpenseService {
    // Declared as final so that the variables are never reassigned.
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    // Constant defining page size (specifically for readExpenses() method).
    private static final int PAGE_SIZE = 10;

    // Constructor assigns user and expense repositories.
    public ExpenseService(UserRepository userRepository, 
        ExpenseRepository expenseRepository) {
            this.userRepository = userRepository;
            this.expenseRepository = expenseRepository;
    }

    // Used to create an expense and save it to the database.
    // It is ExpenseResponse type to prevent the client from seeing
    // all data from the Expense entity in JSON format. Includes 
    // user ID parameter to enforce ownership of the expense and 
    // ExpenseRequest parameter because requests and responses are 
    // passed through the controller, instead of entities.
    public ExpenseResponse createExpense(UUID userId, 
        ExpenseRequest req) {
            // Define user by checking if it exists.
            User user = checkIfUserExists(userId);
            
            // Data validation.
            validateAmount(req.getAmount());
            validateCategory(req.getCategory());
            validateDate(req.getDate());

            // Created expense entity based on passed request.
            Expense expense = new Expense(req.getName(),
                // Enforce two decimal places in JSON response.
                req.getAmount().setScale(2, RoundingMode.HALF_UP), 
                req.getCategory(), req.getDate(), user);

            // Save the expense to the database.
            Expense savedExpense = expenseRepository.save(expense);

            // Return the saved entity through the response.
            return mapEntityToResponse(savedExpense);
    }

    // Used to retrieve a page of expenses. Optional parameters used to enable the 
    // omission of the category, month, or year on the controller layer to match
    // the API endpoint table design artifact.
    public Page<ExpenseResponse> readExpenses(UUID userId, int page, 
        Optional<String> category, Optional<Integer> month, 
        Optional<Integer> year) {
            // Data validation.
            checkIfUserExists(userId);
            validatePage(page);
            validateMonthYear(month, year);
        
            // Contains pagination data. Page size will not be adjustable.
            Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("date").descending());
            Page<Expense> pageResults; // Page of expenses to be assigned and returned.

            // To check if the optional parameters exist and are not null.
            boolean isFilteredByCategory = category.isPresent() && !category.get().isBlank();
            boolean isFilteredByMonth = month.isPresent() && year.isPresent();

            // Both category and month filtering options are selected.
            // Corresponding endpoint:
            // (/expenses?userId=&page=&category=&month=).
            if(isFilteredByCategory && isFilteredByMonth) {
                // Get beginning and end of selected month.
                LocalDate start = LocalDate.of(year.get(), month.get(), 1);
                LocalDate end = start.plusMonths(1).minusDays(1);

                // Corresponding repository method to be returned.
                pageResults = expenseRepository.findByUserUserIdAndCategoryAndDateBetween(
                    userId, category.get(), start, end, pageable
                );
            // Category filtering options are selected.
            // Corresponding endpoint:
            // (/expenses?userId=&page=&category=).
            } else if (isFilteredByCategory) {
                // Corresponding repository method to be returned.
                pageResults = expenseRepository.findByUserUserIdAndCategory(
                    userId, category.get(), pageable
                );
            // Month filtering options are selected.
            // Corresponding endpoint:
            // (/expenses?userId=&page=&month=).
            } else if (isFilteredByMonth) {
                // Get beginning and end of selected month.
                LocalDate start = LocalDate.of(year.get(), month.get(), 1);
                LocalDate end = start.plusMonths(1).minusDays(1);

                // Corresponding repository method to be returned.
                pageResults = expenseRepository.findByUserUserIdAndDateBetween(
                    userId, start, end, pageable
                );
            // No filtering options are selected.
            // Corresponding endpoint:
            // (/expenses?userId=&page=).
            } else {
                // Corresponding repository method to be returned.
                pageResults = expenseRepository.findByUserUserId(userId, pageable);
            }

            // Return the page results mapped to the entity, which is mapped to the response DTO.
            return pageResults.map(e -> mapEntityToResponse(e));
    }

    // Used to update a specific expense and save the updated expense to the database.
    public ExpenseResponse updateExpense(UUID userId, UUID expenseId, ExpenseRequest req) {
        // Define expense by checking if it exists.
        Expense expense = checkIfExpenseExists(userId, expenseId);

        // Data validation
        validateAmount(req.getAmount());
        validateCategory(req.getCategory());
        validateDate(req.getDate());

        // Map the expense to the information received from the DTO.
        expense.setName(req.getName());
        // Enforce two decimal places in JSON response.
        expense.setAmount(req.getAmount().setScale(2, RoundingMode.HALF_UP));
        expense.setCategory(req.getCategory());
        expense.setDate(req.getDate());

        // Save the updated expense to the database.
        Expense savedExpense = expenseRepository.save(expense);

        // Return the saved expense to the database.
        return mapEntityToResponse(savedExpense);
    }

    // Used to delete a specific expense.
    public void deleteExpense(UUID userId, UUID expenseId) {
        // Define expense by checking if it exists.
        Expense expense = checkIfExpenseExists(userId, expenseId);

        // Delete specified expense from the database.
        expenseRepository.delete(expense);
    }

    // Required to map the Expense entity to the ExpenseResponse DTO.
    private ExpenseResponse mapEntityToResponse(Expense expense) {
        return new ExpenseResponse(expense.getExpenseId(),
            expense.getName(), expense.getAmount(),
            expense.getCategory(), expense.getDate()
        );
    }

    // Checks if the user exists.
    private User checkIfUserExists(UUID userId) {
        // If user does not exist, throw HTTP error 404.
        return userRepository.findById(userId).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist."));
    }

    // Checks if the expense exists.
    private Expense checkIfExpenseExists(UUID userId, UUID expenseId) {
        // If expense does not exist, throw HTTP error 404.
        return expenseRepository.findByUserUserIdAndExpenseId(userId, expenseId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Expense does not exist."));
    }

    // Validates amount field when creating or updating an expense.
    private void validateAmount(BigDecimal amount) {
        // Check if amount is null. If it is, throw HTTP error 400.
        if (amount == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must" +
                " not be null.");
        }

        // Check if amount is less than or equal to 0. If it is, throw HTTP error 400.
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must" +
                " be greater than 0.");
        }

        // Check if amount has more than two decimal places. If it is, throw HTTP error 400.
        if (amount.scale() > 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount cannot" +
                " contain greater than two decimal places.");
        }
    }

    // Validates category field when creating or updating an expense.
    private void validateCategory(String category) {
        // Check if category is null. If it is, throw HTTP error 400.
        if (category == null || category.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category must" +
                " not be null or blank.");
        }
    }

    // Validates date field when creating or updating an expense.
    private void validateDate(LocalDate date) {
        // Check if category is null. If it is, throw HTTP error 400.
        if (date == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date must not" +
                " be null.");
        }

        // Check if date is at a future time from request. If it is, throw HTTP error 400.
        if (date.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date cannot be" +
                " in the future.");
        }
    }

    // Validate page number.
    private void validatePage(int page) {
        // Check if page number is negative. If it is, throw HTTP error 400.
        if (page < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page number" +
                " cannot be negative.");
        }
    }

    // Validate optional month and year parameters in URL.
    private void validateMonthYear(Optional<Integer> month, Optional<Integer> year) {
        // Check if both month and year are present in URL, as they are required together.
        // If not, throw HTTP error 400.
        if ((month.isPresent() && year.isEmpty()) || (month.isEmpty() && year.isPresent())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Both month and" +
                " year parameters are required in URL.");
        }

        // If both are present in URL.
        if (month.isPresent() && year.isPresent()) {
            // Assign month value to int for conditional.
            int monthValue = month.get();

            // Check if month is less than 1 (January) or greater than 12 (Decemeber).
            // If they are, throw HTTP error 400.
            if (monthValue < 1 || monthValue > 12) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Month" +
                " parameter must be a valid month (01-12).");
            }
        }
    }
}
