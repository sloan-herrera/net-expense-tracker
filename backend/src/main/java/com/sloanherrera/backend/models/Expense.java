package com.sloanherrera.backend.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
/**
 * An entity that represents an expense in the MySQL relational database.
 */
@Entity
@Table(name = "expenses")
public class Expense {
    // ID value is required to be randomly generated, so UUID used to ensure
    // unique values, as opposed to sequential ID generation.
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "expense_id", nullable = false, updatable = false)
    private UUID expenseId;

    // Title of the expense.
    @Column(name = "name", nullable = false)
    private String name;

    // Amount spent on the expense. Precision defines the maximum amount of digits
    // allowed and scale defines the number of digits after the decimal. BigDecimal
    // is used instead of double for accuracy.
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    // Category of the expense. Expense categories will be provided in a dropdown
    // list in the frontend part of the system.
    @Column(name = "category", nullable = false)
    private String category;

    // Date of transaction for the expense.
    @Column(name = "date", nullable = false)
    private LocalDate date;

    // Foreign key, representing the user that the expense is matched to. Columns
    // are joined on "user_id" from the "users" table.
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Empty constructor, required by Spring Boot JPA.
    public Expense() {}

    // Overloaded constructor to make setting values easier.
    public Expense(String name, BigDecimal amount, String category, LocalDate date, User user) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.user = user;
    }

    // Getters and setters

    // No setter for ID, as it is automatically generated.
    public UUID getExpenseId() {
        return expenseId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
