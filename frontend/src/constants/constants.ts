// Constants used as opposed to hardcoded values for modularity.

// URL for backend API.
export const API_URL = import.meta.env.VITE_API_URL;

// Expense categories.
export const CATEGORIES = [
  "Housing",
  "Utilities",
  "Food",
  "Transportation",
  "Health",
  "Shopping",
  "Entertainment",
  "Subscriptions",
  "Miscellaneous",
];

// Months of the year for expense filtering.
export const MONTHS = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

// Years for expense filtering. After the system is live, the first
// patch will involve creating an endpoint to get years from backend
// so that they generate dynamically with user expenses.
export const YEARS = [2026, 2025, 2024, 2023, 2022];
