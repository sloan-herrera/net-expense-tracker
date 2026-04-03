import { Link, useNavigate } from "react-router-dom";
import SignInForm from "../components/SignInForm";
import { loginUser } from "../services/authService";

// Displays the content of the sign-in page.
function SignIn() {
  // Used to navigate to other pages.
  const navigate = useNavigate();

  // Handles login by calling backend authentication endpoint.
  const handleLogin = async (email: string, password: string) => {
    // Attempt to call authService loginUser function with passed input.
    try {
      await loginUser({ email, password });

      // Redirect to dashboard on login.
      navigate("/dashboard");
      // If an error occurs, catch it, return the message, and alert user.
    } catch (error) {
      console.error("Error logging user in", error);
      alert("Login Failed.");
    }
  };

  return (
    <>
      <h1 className="text-center text-light mt-5 mb-5">NetExpenseTracker</h1>
      {/* SignInForm component embedded in page with handleLogin passed as 
      parameter. */}
      <SignInForm handleSubmit={handleLogin} />
      <p className="text-light text-center mt-2 mb-1">Don't have an account?</p>
      {/* Link enabling user to create an account. */}
      <p className="text-center">
        <Link to="/registration" className="text-light text-decoration-ul">
          Register Here
        </Link>
      </p>
    </>
  );
}

export default SignIn;
