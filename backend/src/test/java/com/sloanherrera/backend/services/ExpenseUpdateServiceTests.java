package com.sloanherrera.backend.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Provides automated testing for updating expenses in ExpenseService.java, the service 
 * layer class for expense management. NOTE: The project initially used manual testing 
 * to ensure functionality; automated testing was implemented retroactively.
 */

class ExpenseUpdateServiceTests extends BaseExpenseServiceTests {
    @Disabled("Not implemented yet")
    @Test
    void updateExpense_validRequest_returnsUpdatedExpense() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_nonexistentExpense_throwsNotFound() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_wrongUser_throwsNotFound() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_nullAmount_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_zeroAmount_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_negativeAmount_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_amountMoreThanTwoDecimals_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_nullCategory_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_blankCategory_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_nullDate_throwsBadRequest() {

    }

    @Disabled("Not implemented yet")
    @Test
    void updateExpense_futureDate_throwsBadRequest() {

    }
}
