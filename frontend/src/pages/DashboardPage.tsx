import { useState } from "react";
import ExpenseForm from "../components/ExpenseForm";
import Navbar from "../components/Navbar";
import ExpenseTable from "../components/ExpenseTable";
import type { Expense } from "../types/types";
import UpdateExpenseModal from "../components/UpdateExpenseModal";

//TO DO: Replace any frontend exclusive functionality with API calls.

// Displays content of the dashboard page.
function Dashboard() {
  // Set state for expenses using the Expense type as an array.
  const [expenses, setExpenses] = useState<Expense[]>([]);

  // Set state for showing update expense modal.
  const [showUpdateModal, setShowUpdateModal] = useState(false);

  // Create expense function passed to ExpenseForm to handle the creation of new expenses
  // without removing old ones.
  function createExpense(expense: Expense) {
    setExpenses(expenses.concat(expense));
  }

  // Opens update expense modal.
  function openUpdateModal(): void {
    setShowUpdateModal(true);
  }

  // Closes update expense modal.
  function closeUpdateModal(): void {
    setShowUpdateModal(false);
  }

  return (
    <>
      {/* Reusable navigation bar component. */}
      <Navbar />
      <h1 className="text-center text-light mt-2 mb-5">
        Welcome to NetExpenseTracker!
      </h1>
      {/* ExpenseForm uses createExpense function as parameter. */}
      <ExpenseForm createExpense={createExpense} />
      {/* Only generate the table if there are more than zero expenses. Pass 
      open update expense modal to table. */}
      {expenses.length > 0 && (
        <ExpenseTable expenses={expenses} openUpdateModal={openUpdateModal} />
      )}
      {/* UpdateExpenseModal called through page, but open behavior is passed 
      to the above table. closeUpdateModal is passed to UpdateExpenseModal to
      give the ability to close itself. */}
      <UpdateExpenseModal
        show={showUpdateModal}
        handleClose={closeUpdateModal}
      />
    </>
  );
}

export default Dashboard;
