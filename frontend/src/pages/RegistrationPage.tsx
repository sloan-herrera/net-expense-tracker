import { Link, useNavigate } from "react-router-dom";
import { Container, Row, Col, Button } from "react-bootstrap";

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
      {/* Container with dark border centered on the screen. */}
      <Container className="border border-dark text-center mt-5 p-4">
        {/* Row with large bottom margin. */}
        <Row className="mb-4">
          <Col>
            <h1 className="text-light">Register</h1>
          </Col>
        </Row>
        {/* Row with medium bottom margin. */}
        <Row className="mb-2">
          <Col>
            {/* Rounded dark button with light text enabling the user to create account. */}
            <Button
              variant="dark"
              className="rounded-pill text-light"
              onClick={handleRegistration}
            >
              Register
            </Button>
          </Col>
        </Row>
      </Container>
      <p className="text-light text-center mt-2 mb-1">
        Already have an account?
      </p>
      {/* Link enabling user to login. */}
      <p className="text-center">
        <Link to="/" className="text-light text-decoration-none">
          Sign In Here
        </Link>
      </p>
    </>
  );
}

export default Register;
