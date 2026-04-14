import { BrowserRouter, Routes, Route } from "react-router-dom";
import SignIn from "./pages/SignInPage";
import Register from "./pages/RegistrationPage";
import Dashboard from "./pages/DashboardPage";
import Contact from "./pages/ContactPage";
import { useEffect, useState } from "react";
import { readExpenses } from "./services/expenseService";
import ProtectedRoute from "./components/ProtectedRoute";

// Entry point for the application acts as a routing hub for each page.
function App() {
  // Set states for storing checks on user authentication for protected routes.
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  // Check if user session is still active when application loads.
  useEffect(() => {
    // Calls a protected endpoint to check if user is authenticated.
    async function checkAuthentication() {
      // Attempt to retrieve expenses to verify authentication.
      try {
        await readExpenses(0);
        setIsAuthenticated(true);
        // If the request fails, the user is not authenticated.
      } catch (error) {
        setIsAuthenticated(false);
        console.error("Error checking user authentication", error);
        // Authentication check is finished so stop loading state.
      } finally {
        setIsLoading(false);
      }
    }

    checkAuthentication();
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        {/* Default page is the Sign In/Login page, as user account is required
          to access other pages (except registration page). */}
        <Route
          path="/"
          element={<SignIn setIsAuthenticated={setIsAuthenticated} />}
        />
        {/* Route to Registration/Register page. */}
        <Route path="/registration" element={<Register />} />
        {/* Route to Dashboard/Expenses page. */}
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute
              isAuthenticated={isAuthenticated}
              isLoading={isLoading}
            >
              <Dashboard setIsAuthenticated={setIsAuthenticated} />
            </ProtectedRoute>
          }
        />
        {/* Route to Contact page. */}
        <Route
          path="/contact"
          element={
            <ProtectedRoute
              isAuthenticated={isAuthenticated}
              isLoading={isLoading}
            >
              <Contact setIsAuthenticated={setIsAuthenticated} />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
