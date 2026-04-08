// Functions in this file directly communicate with backend for expense
// API calls.

import axios from "axios";
import type { Expense, ExpensePage, ExpenseRequest } from "../types/types";
import { getCsrfToken } from "./csrfService";
import { API_URL } from "../constants/constants";

// Expense creation API call.
export async function createExpense(
  expenseData: ExpenseRequest,
): Promise<Expense> {
  // Retrieve and store CSRF token as constant.
  const token = await getCsrfToken();

  // Attempt to call POST /expenses endpoint.
  try {
    const res = await axios.post(`${API_URL}/expenses`, expenseData, {
      // Set session and CSRF cookies.
      withCredentials: true,
      headers: {
        "X-XSRF-TOKEN": token,
      },
    });

    // Return the response data.
    return res.data;
    // If an error occurs, catch it and return the message.
  } catch (error) {
    console.error("Error creating expense", error);
    throw error;
  }
}

// Expense page retrieval API call. Includes optional filtering parameters.
export async function readExpenses(
  page: number,
  category?: string,
  month?: number,
  year?: number,
): Promise<ExpensePage> {
  // Attempt to call GET /expenses endpoint.
  try {
    // Store parameters in constant.
    const params: {
      page: number;
      category?: string;
      month?: number;
      year?: number;
    } = { page };

    // Set category filter if present in endpoint URL.
    if (category && category !== "") {
      params.category = category;
    }

    // Set month and year filter if present in endpoint URL. Month and
    // year must be included together, as stated by business
    // requirements.
    if (month && year) {
      params.month = month;
      params.year = year;
    }

    // Call endpoint with session credentials and parameters.
    const res = await axios.get(`${API_URL}/expenses`, {
      withCredentials: true,
      params,
    });

    // Return the response data.
    return res.data;
    // If an error occurs, catch it and return the message.
  } catch (error) {
    console.error("Error retrieving expenses", error);
    throw error;
  }
}

// Expense updating API call.
export async function updateExpense(
  expenseId: string,
  expenseData: ExpenseRequest,
): Promise<Expense> {
  // Retrieve and store CSRF token as constant.
  const token = await getCsrfToken();

  // Attempt to call PUT /expenses/{expenseId} endpoint.
  try {
    const res = await axios.put(
      `${API_URL}/expenses/${expenseId}`,
      expenseData,
      {
        // Set session and CSRF cookies.
        withCredentials: true,
        headers: {
          "X-XSRF-TOKEN": token,
        },
      },
    );

    // Return the response data.
    return res.data;
    // If an error occurs, catch it and return the message.
  } catch (error) {
    console.error("Error updating expense", error);
    throw error;
  }
}

// Expense deletion API call.
export async function deleteExpense(expenseId: string): Promise<void> {
  // Retrieve and store CSRF token as constant.
  const token = await getCsrfToken();

  // Attempt to call DELETE /expenses/{expenseId} endpoint.
  try {
    await axios.delete(`${API_URL}/expenses/${expenseId}`, {
      // Set session and CSRF cookies.
      withCredentials: true,
      headers: {
        "X-XSRF-TOKEN": token,
      },
    });

    // If an error occurs, catch it and return the message.
  } catch (error) {
    console.error("Error deleting expense", error);
    throw error;
  }
}
