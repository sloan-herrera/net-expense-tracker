// Functions in this file directly communicate with backend for authentication
// API calls.

import axios from "axios";
import type { UserRequest } from "../types/types";

// URL for backend API.
const API_URL = import.meta.env.VITE_API_URL;

// Retrieve CSRF token from backend to use in requests.
async function getCsrfToken() {
  // Attempt to call /csrf endpoint.
  try {
    const res = await axios.get(`${API_URL}/csrf`, {
      withCredentials: true,
    });

    // Return the CSRF token.
    return res.data.token;
    // If an error occurs, catch it and return the message.
  } catch (error) {
    console.log("Error fetching CSRF token", error);
    throw error;
  }
}

// User registration API call.
export async function registerUser(userData: UserRequest) {
  // Attempt to call /register endpoint with input data from UserRequest.
  try {
    const res = await axios.post(`${API_URL}/register`, userData, {
      // Set session and CSRF cookies.
      withCredentials: true,
    });

    // Return the response data.
    return res.data;
    // If an error occurs, catch it and return the message.
  } catch (error) {
    console.error("Error creating user account", error);
    throw error;
  }
}

// User login API call.
export async function loginUser(userData: UserRequest) {
  // Attempt to call /login endpoint with input data from UserRequest.
  try {
    const res = await axios.post(`${API_URL}/login`, userData, {
      // Set session and CSRF cookies.
      withCredentials: true,
    });

    // Return the response data.
    return res.data;
    // If an error occurs, catch it and return the message.
  } catch (error) {
    console.error("Error authenticating user", error);
    throw error;
  }
}

// User logout API call.
export async function logoutUser() {
  const token = await getCsrfToken();

  // Attempt to call /logout endpoint.
  try {
    await axios.post(
      `${API_URL}/logout`,
      {},
      {
        // Set session and CSRF cookies.
        withCredentials: true,
        headers: {
          "X-XSRF-TOKEN": token,
        },
      },
    );

    // If an error occurs, catch it and return the message.
  } catch (error) {
    console.error("Error logging out user", error);
    throw error;
  }
}
