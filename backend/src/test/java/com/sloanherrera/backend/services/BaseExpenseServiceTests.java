package com.sloanherrera.backend.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sloanherrera.backend.models.Expense;
import com.sloanherrera.backend.repositories.ExpenseRepository;
import com.sloanherrera.backend.repositories.UserRepository;

/**
 * Provides Mockito setup for automated testing for ExpenseService.java, the service layer
 * class for expense management. NOTE: The project initially used manual testing to ensure
 * functionality; automated testing was implemented retroactively.
 */

@ExtendWith(MockitoExtension.class) // Use Mockito to generate mock repositories and data.
abstract class BaseExpenseServiceTests {
    // Mock repositories to isolate ExpenseService from database.
    @Mock
    protected UserRepository userRepository;

    @Mock
    protected ExpenseRepository expenseRepository;

    // Inject mock repositories into the ExpenseService being tested.
    @InjectMocks
    protected ExpenseService expenseService;

    // Capture Expense objects passed to the repository to verify their field values.
    @Captor
    protected ArgumentCaptor<Expense> expenseCaptor;
}
