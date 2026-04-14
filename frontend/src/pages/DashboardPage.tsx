import { useEffect, useState } from "react";
import ExpenseForm from "../components/ExpenseForm";
import Navbar from "../components/Navbar";
import ExpenseTable from "../components/ExpenseTable";
import type { Expense, ExpenseRequest } from "../types/types";
import UpdateExpenseModal from "../components/UpdateExpenseModal";
import {
  readExpenses,
  createExpense as createExpenseAPI,
  updateExpense as updateExpenseAPI,
  deleteExpense as deleteExpenseAPI,
} from "../services/expenseService";

// Define object for React Properties
type Properties = {
  setIsAuthenticated: (isAuthenticated: boolean) => void;
};

// Displays content of the dashboard page.
function Dashboard({ setIsAuthenticated }: Properties) {
  // Set state for expenses using the Expense type as an array.
  const [expenses, setExpenses] = useState<Expense[]>([]);

  // Set state for showing update expense modal.
  const [showUpdateModal, setShowUpdateModal] = useState(false);

  // Store and set state for selected expense for editing in dashboard. Initially set to
  // null until UpdateExpenseModal is open.
  const [selectedExpense, setSelectedExpense] = useState<Expense | null>(null);

  // Store and set state for current page and total pages.
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  // Set states for selected filters. Numeric values (month/year) initially set to undefined
  // to default to no filters.
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedMonth, setSelectedMonth] = useState<number | undefined>(
    undefined,
  );
  const [selectedYear, setSelectedYear] = useState<number | undefined>(
    undefined,
  );

  // Load expenses from the backend based on page and optional filtering parameters.
  async function loadExpenses(
    page: number,
    category?: string,
    month?: number,
    year?: number,
  ): Promise<void> {
    // Attempt to call readExpenses function with passed parameters.
    try {
      const expensePage = await readExpenses(page, category, month, year);

      // Set values for expenses and page(s) based on page data.
      setExpenses(expensePage.content);
      setCurrentPage(expensePage.number);
      setTotalPages(expensePage.totalPages);
      // If an error occurs, catch it, return the message, and alert user.
    } catch (error) {
      console.error("Error loading expenses", error);
      alert("Loading Expenses Failed.");
    }
  }

  // When page first renders, load the first page of expenses from the backend.
  useEffect(() => {
    async function init() {
      await loadExpenses(0);
    }

    init();
  }, []);

  // Create expense through backend and reload the UI to include new expense.
  async function createExpense(expenseData: ExpenseRequest): Promise<void> {
    // Attempt to call createExpense function from expenseService.ts with passed input
    // data and reload the page with loadExpenses function at first page with filters
    // (if any are active).
    try {
      await createExpenseAPI(expenseData);
      await loadExpenses(0, selectedCategory, selectedMonth, selectedYear);
      // If an error occurs, catch it, return the message, and alert user.
    } catch (error) {
      console.error("Error creating expense", error);
      alert("Creating Expense Failed.");
    }
  }

  // Opens update modal and initializes the selected expense for editing.
  function openUpdateModal(expense: Expense): void {
    setSelectedExpense(expense);
    setShowUpdateModal(true);
  }

  // Closes update modal and clears the selected expense and edit state.
  function closeUpdateModal(): void {
    setSelectedExpense(null);
    setShowUpdateModal(false);
  }

  // Updates selected expense through backend and reload the UI to include updated
  // expense.
  async function updateExpense(expenseData: ExpenseRequest): Promise<void> {
    // Cancel if there is no selected expense.
    if (!selectedExpense) {
      return;
    }

    // Attempt to call updateExpense function from expenseService.ts with passed input
    // data and reload the page with loadExpenses function on the current page with
    // filters (if any are active).
    try {
      await updateExpenseAPI(selectedExpense.expenseId, expenseData);
      closeUpdateModal(); // Close UpdateExpenseModal after expense is updated.
      await loadExpenses(
        currentPage,
        selectedCategory,
        selectedMonth,
        selectedYear,
      );
      // If an error occurs, catch it, return the message, and alert user.
    } catch (error) {
      console.error("Error updating expense", error);
      alert("Updating Expense Failed.");
    }
  }

  // Deletes selected expense through backend and reload the UI to show that the expense
  // was removed.
  async function deleteExpense(expenseId: string): Promise<void> {
    // Attempt to call deleteExpense function from expenseService.ts with passed expense
    // ID data and reload the page with loadExpenses function on the current page with
    // filters (if any are active).
    try {
      await deleteExpenseAPI(expenseId);
      await loadExpenses(
        currentPage,
        selectedCategory,
        selectedMonth,
        selectedYear,
      );
      // If an error occurs, catch it, return the message, and alert user.
    } catch (error) {
      console.error("Error deleting expense", error);
      alert("Deleting Expense Failed.");
    }
  }

  // Applies selected filters and resets UI to first page.
  async function applyFilters(): Promise<void> {
    // Month and year filters must be applied together for proper date filtering.
    if ((!selectedMonth && selectedYear) || (selectedMonth && !selectedYear)) {
      alert("Month and year filters must be applied together.");
      return;
    }

    // Attempt to reload first page with applied filters.
    try {
      await loadExpenses(0, selectedCategory, selectedMonth, selectedYear);
      // If an error occurs, catch it, return the message, and alert user.
    } catch (error) {
      console.error("Error applying filters", error);
      alert("Applying Filters Failed.");
    }
  }

  // Clears selected filters and resets UI to first page.
  async function clearFilters(): Promise<void> {
    setSelectedCategory("");
    setSelectedMonth(undefined);
    setSelectedYear(undefined);

    // Attempt to reload first page after clearing filters.
    try {
      await loadExpenses(0);
      // If an error occurs, catch it, return the message, and alert user.
    } catch (error) {
      console.error("Error clearing filters", error);
      alert("Clearing Filters Failed.");
    }
  }

  // Loads previous page of expenses.
  async function prevPage(): Promise<void> {
    // Only if current page is greater than zero (first page).
    if (currentPage > 0) {
      // Attempt to load previous page.
      try {
        await loadExpenses(
          currentPage - 1,
          selectedCategory,
          selectedMonth,
          selectedYear,
        );
        // If an error occurs, catch it, return the message, and alert user.
      } catch (error) {
        console.error("Error navigating to previous page", error);
        alert("Previous Page Navigation Failed.");
      }
    }
  }

  // Loads next page of expenses.
  async function nextPage(): Promise<void> {
    // Only if current page is less than the last page. Pages start at zero, but for
    // UX purposes, it needs to start at one, which is why the last page is totalPages
    // - 1.
    if (currentPage < totalPages - 1) {
      // Attempt to load next page.
      try {
        await loadExpenses(
          currentPage + 1,
          selectedCategory,
          selectedMonth,
          selectedYear,
        );
        // If an error occurs, catch it, return the message, and alert user.
      } catch (error) {
        console.error("Error navigating to next page", error);
        alert("Next Page Navigation Failed.");
      }
    }
  }

  return (
    <>
      {/* Reusable navigation bar component. */}
      <Navbar setIsAuthenticated={setIsAuthenticated} />
      <h1 className="text-center text-light mt-2 mb-5">
        Welcome to NetExpenseTracker!
      </h1>
      {/* ExpenseForm uses createExpense function as parameter. */}
      <ExpenseForm createExpense={createExpense} />
      {/* ExpenseTable called through page with passed parameters. */}
      <ExpenseTable
        expenses={expenses}
        selectedCategory={selectedCategory}
        selectedMonth={selectedMonth}
        selectedYear={selectedYear}
        currentPage={currentPage}
        totalPages={totalPages}
        openUpdateModal={openUpdateModal}
        deleteExpense={deleteExpense}
        setSelectedCategory={setSelectedCategory}
        setSelectedMonth={setSelectedMonth}
        setSelectedYear={setSelectedYear}
        applyFilters={applyFilters}
        clearFilters={clearFilters}
        prevPage={prevPage}
        nextPage={nextPage}
      />
      {/* UpdateExpenseModal called through page with passed parameters. */}
      <UpdateExpenseModal
        key={selectedExpense?.expenseId}
        show={showUpdateModal}
        selectedExpense={selectedExpense}
        handleClose={closeUpdateModal}
        updateExpense={updateExpense}
      />
    </>
  );
}

export default Dashboard;
