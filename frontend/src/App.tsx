import { BrowserRouter, Routes, Route } from "react-router-dom";
import SignIn from "./pages/SignInPage";
import Register from "./pages/RegistrationPage";
import Dashboard from "./pages/DashboardPage";
import Contact from "./pages/ContactPage";

// Entry point for the application acts as a routing hub for each page.
function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Default page is the Sign In/Login page, as user account is required
          to access other pages. */}
        <Route path="/" element={<SignIn />} />
        {/* Route to Registration/Register page. */}
        <Route path="/registration" element={<Register />} />
        {/* Route to Dashboard/Expenses page. */}
        <Route path="/dashboard" element={<Dashboard />} />
        {/* Route to Contact page. */}
        <Route path="/contact" element={<Contact />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
