import { useState } from "react";
import ExpenseForm from "../components/ExpenseForm";
import Navbar from "../components/Navbar";
import ExpenseTable from "../components/ExpenseTable";
import type { Expense } from "../types/types";

// Displays content of the dashboard page.
function Dashboard() {
  // Set state for expenses using the Expense type as an array.
  const [expenses, setExpenses] = useState<Expense[]>([]);

  // Create expense function passed to ExpenseForm to handle the creation of new expenses
  // without removing old ones.
  function createExpense(expense: Expense) {
    setExpenses(expenses.concat(expense));
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
      {/* Only generate the table if there are more than zero expenses. */}
      {expenses.length > 0 && <ExpenseTable expenses={expenses} />}
    </>
  );
}

export default Dashboard;
