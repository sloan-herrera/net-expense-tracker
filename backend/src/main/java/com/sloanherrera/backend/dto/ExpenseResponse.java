package com.sloanherrera.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This is an ExpenseResponse DTO (Data Transfer Object) to ensure that entities are
 * not directly accessed. It also makes communication between frontend and 
 * backend easier. Response is server to client.
 */
public class ExpenseResponse {
    // User that the expense is owned by is not included for security purposes.
    private UUID expenseId;
    private String name;
    private BigDecimal amount;
    private String category;
    private LocalDate date;

    // Empty constructor required by Spring Boot.
    public ExpenseResponse() {}

    public ExpenseResponse(UUID expenseId, String name, BigDecimal amount, String category, 
        LocalDate date) {
            this.expenseId = expenseId;
            this.name = name;
            this.amount = amount;
            this.category = category;
            this.date = date;
    }

    // Getters and setters.

    public UUID getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(UUID expenseId) {
        this.expenseId = expenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
