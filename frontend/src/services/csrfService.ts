// The function in this file retrieves CSRF token from backend to
// include in protected requests.

import axios from "axios";
import { API_URL } from "../constants/constants";

// Retrieve CSRF token from backend to use in requests.
export async function getCsrfToken(): Promise<string> {
  // Attempt to call /csrf endpoint.
  try {
    const res = await axios.get(`${API_URL}/csrf`, {
      withCredentials: true,
    });

    // Return the CSRF token.
    return res.data.token;
    // If an error occurs, catch it and return the message.
  } catch (error) {
    console.error("Error fetching CSRF token", error);
    throw error;
  }
}
