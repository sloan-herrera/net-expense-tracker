// Functions in this file directly communicate with backend for authentication
// API calls.

import axios from "axios";
import type { UserRequest, UserResponse } from "../types/types";
import { API_URL } from "../constants/constants";
import { getCsrfToken } from "./csrfService";

// User registration API call.
export async function registerUser(
  userData: UserRequest,
): Promise<UserResponse> {
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
export async function loginUser(userData: UserRequest): Promise<UserResponse> {
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
export async function logoutUser(): Promise<void> {
  // Retrieve and store CSRF token as constant.
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
