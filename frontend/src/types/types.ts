// Any shared types are defined in this file.

// Define expense object.
export type Expense = {
  expenseId: string;
  name: string;
  amount: number;
  category: string;
  date: string;
};

// Represents a page of expenses sent from backend.
export type ExpensePage = {
  content: Expense[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
};

// Expense object sent to backend for creating or updating
// an expense. Matches the shape of ExpenseRequest DTO in
// backend.
export type ExpenseRequest = {
  name: string;
  amount: number;
  category: string;
  date: string;
};

// Represents user data sent to backend for authentication.
// Matches the shape of UserRequest DTO in backend.
export type UserRequest = {
  email: string;
  password: string;
};

// Represents user data sent from backend for authentication.
// Matches the shape of UserResponse DTO in backend.
export type UserResponse = {
  userId: string;
  email: string;
};
