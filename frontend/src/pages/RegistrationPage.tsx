import { Link, useNavigate } from "react-router-dom";
import RegistrationForm from "../components/RegistrationForm";

// Displays the content of the registration page.
function Register() {
  // Used to navigate to other pages.
  const navigate = useNavigate();
  // Handles registration by redirecting user to sign-in on button
  // click (temporary until authentication is implemented).
  const handleRegistration = () => {
    navigate("/");
  };

  return (
    <>
      <h1 className="text-center text-light mt-5 mb-5">NetExpenseTracker</h1>
      {/* RegistrationForm component embedded in page with handleRegistration 
      passed as parameter. */}
      <RegistrationForm handleSubmit={handleRegistration} />
      <p className="text-light text-center mt-2 mb-1">
        Already have an account?
      </p>
      {/* Link enabling user to login. */}
      <p className="text-center">
        <Link to="/" className="text-light text-decoration-ul">
          Sign In Here
        </Link>
      </p>
    </>
  );
}

export default Register;
