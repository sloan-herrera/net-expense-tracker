// This file contains data validation relevant to frontend user
// input.

import { CATEGORIES } from "../constants/constants";

// Validates registration form input.
export function validateRegistration(email: string, password: string) {
  const trimmedEmail = email.trim(); // Clear leading or trailing spaces in email.

  // Check if email field is empty.
  if (trimmedEmail === "") {
    return { isValid: false, message: "Email is required." };
  }

  // Check if email field contains a valid email address with proper format.
  if (!trimmedEmail.includes("@") || !trimmedEmail.includes(".")) {
    return { isValid: false, message: "Enter a valid email address." };
  }

  // Check if password field is empty. Trim to ensure no leading or trailing spaces
  // during validation.
  if (password.trim() === "") {
    return { isValid: false, message: "Password is required." };
  }

  // Check if password field is less than 12 characters, the minimum allowed by
  // backend.
  if (password.length < 12) {
    return {
      isValid: false,
      message: "Password must be at least twelve characters.",
    };
  }

  // Registration input is valid.
  return { isValid: true, message: "" };
}

// Validates login form input.
export function validateLogin(email: string, password: string) {
  const trimmedEmail = email.trim(); // Clear leading or trailing spaces in email.

  // Check if email field is empty.
  if (trimmedEmail === "") {
    return { isValid: false, message: "Email is required." };
  }

  // Check if email field contains a valid email address with proper format.
  if (!trimmedEmail.includes("@") || !trimmedEmail.includes(".")) {
    return { isValid: false, message: "Enter a valid email address." };
  }

  // Check if password field is empty. Trim to ensure no leading or trailing spaces
  // during validation.
  if (password.trim() === "") {
    return { isValid: false, message: "Password is required." };
  }

  // Login input is valid.
  return { isValid: true, message: "" };
}

// Validates expense form input for both creating and updating expenses.
export function validateExpense(
  name: string,
  amount: string,
  category: string,
  date: string,
) {
  const trimmedName = name.trim(); // Clear leading or trailing spaces in name.
  const convertedAmount = Number(amount); // Convert input string to number.
  const selectedDate = new Date(date); // Store selected date.
  const today = new Date(); // Current date for comparing with selected date.

  // Check if name field is empty.
  if (trimmedName === "") {
    return { isValid: false, message: "Expense name is required." };
  }

  // Check if amount field is empty.
  if (amount.trim() === "") {
    return { isValid: false, message: "Expense amount is required." };
  }

  // Check if amount can be converted into a valid number.
  if (isNaN(convertedAmount)) {
    return {
      isValid: false,
      message: "Expense amount must be a valid number.",
    };
  }

  // Check if amount is less than or equal to 0, as expenses cannot be
  // negative or 0.
  if (convertedAmount <= 0) {
    return {
      isValid: false,
      message: "Expense amount must be greater than zero.",
    };
  }

  // Check if category field is empty.
  if (category === "") {
    return { isValid: false, message: "Expense category is required." };
  }

  // Check if category is not one of the predefined options.
  if (!CATEGORIES.includes(category)) {
    return { isValid: false, message: "Select a valid expense category." };
  }

  // Check if date field is empty.
  if (date === "") {
    return { isValid: false, message: "Expense date is required." };
  }

  // Check if date can be converted into a valid date object.
  if (isNaN(selectedDate.getTime())) {
    return { isValid: false, message: "Select a valid expense date." };
  }

  // Clear time values for selectedDate and today so that only date is compared.
  selectedDate.setHours(0, 0, 0, 0);
  today.setHours(0, 0, 0, 0);

  // Check if the selected date is greater than the current date, as date input cannot
  // be in the future.
  if (selectedDate > today) {
    return { isValid: false, message: "Expense date cannot be in the future." };
  }

  // Expense input is valid.
  return { isValid: true, message: "" };
}

// Validates contact form input.
export function validateContactMessage(contactMessage: string) {
  // Trim leading and trailing spaces in contact message input.
  const trimmedMessage = contactMessage.trim();

  // Check if contact message is empty.
  if (trimmedMessage === "") {
    return { isValid: false, message: "Message is required." };
  }

  // Contact message is valid.
  return { isValid: true, message: "" };
}
