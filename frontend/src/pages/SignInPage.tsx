import { Link, useNavigate } from "react-router-dom";
import SignInForm from "../components/SignInForm";

// Displays the content of the sign-in page.
function SignIn() {
  // Used to navigate to other pages.
  const navigate = useNavigate();
  // Handles login by redirecting user to expense dashboard on button
  // click (temporary until authentication is implemented).
  const handleLogin = () => {
    navigate("/dashboard");
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
