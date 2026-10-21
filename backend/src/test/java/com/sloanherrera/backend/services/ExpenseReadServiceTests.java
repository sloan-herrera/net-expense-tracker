package com.sloanherrera.backend.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Provides automated testing for reading expenses in ExpenseService.java, the service 
 * layer class for expense management. NOTE: The project initially used manual testing 
 * to ensure functionality; automated testing was implemented retroactively.
 */

class ExpenseReadServiceTests extends BaseExpenseServiceTests {
    @Disabled("Not implemented yet")
    @Test
    void readExpenses_noFilters_returnsPaginatedExpenses() {
        
    }

    @Disabled("Not implemented yet")
    @Test
    void readExpenses_categoryFilter_returnsMatchingExpenses() {
        
    }

    @Disabled("Not implemented yet")
    @Test
    void readExpenses_monthYearFilter_returnsMatchingExpenses() {
        
    }

    @Disabled("Not implemented yet")
    @Test
    void readExpenses_categoryAndMonthYearFilter_returnsMatchingExpenses() {

    }

    @Disabled("Not implemented yet")
    @Test
    void readExpenses_negativePage_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void readExpenses_monthBelowOne_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void readExpenses_monthAboveTwelve_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void readExpenses_monthWithoutYear_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void readExpenses_yearWithoutMonth_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void readExpenses_nonexistentUser_throwsNotFound() {

    }

}
