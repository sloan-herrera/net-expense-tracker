// Any shared types are defined in this file.

// Define expense object.
export type Expense = {
  name: string;
  amount: string;
  category: string;
  date: string;
};

// Represents user data sent to backend for authentication.
// Matches the shape of UserRequest DTO in backend.
export type UserRequest = {
  email: string;
  password: string;
};
