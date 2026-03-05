import { Link, useNavigate } from "react-router-dom";
import { Container, Row, Col, Button } from "react-bootstrap";

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
      {/* Container with dark border centered on the screen. */}
      <Container className="border border-dark text-center mt-5 p-4">
        {/* Row with large bottom margin. */}
        <Row className="mb-4">
          <Col>
            <h1 className="text-light">Sign In</h1>
          </Col>
        </Row>
        {/* Row with medium bottom margin. */}
        <Row className="mb-2">
          <Col>
            {/* Rounded dark button with light text enabling the user to login. */}
            <Button
              variant="dark"
              className="rounded-pill text-light"
              onClick={handleLogin}
            >
              Sign In
            </Button>
          </Col>
        </Row>
      </Container>
      <p className="text-light text-center mt-2 mb-1">Don't have an account?</p>
      {/* Link enabling user to create an account. */}
      <p className="text-center">
        <Link to="/registration" className="text-light text-decoration-none">
          Register Here
        </Link>
      </p>
    </>
  );
}

export default SignIn;
