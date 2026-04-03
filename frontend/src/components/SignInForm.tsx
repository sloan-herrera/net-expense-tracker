import { useState } from "react";
import { Button, Col, Container, Form, Row } from "react-bootstrap";

// Define object for React Properties.
type Properties = {
  handleSubmit: (email: string, password: string) => Promise<void>;
};

// Form for the sign-in page.
function SignInForm({ handleSubmit }: Properties) {
  // Set states for email and password.
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // Handles form submission by preventing the page from refreshing and
  // passing input values to sign-in page.
  async function handleFormSubmit(e: React.SyntheticEvent<HTMLFormElement>) {
    e.preventDefault();

    // Check if values are empty. If they are, prevent login attempt.
    if (email === "" || password === "") {
      return;
    }

    // Call handleSubmit function from sign-in page.
    await handleSubmit(email, password);

    // Reset values after login attempt.
    setEmail("");
    setPassword("");
  }

  return (
    <>
      {/* Container for sign in form. */}
      <Container className="text-center mt-5 p-4">
        {/* Form header. */}
        <Row className="mb-4">
          <Col>
            <h3 className="text-light">Sign In</h3>
          </Col>
        </Row>
        {/* Form body. */}
        <Form onSubmit={handleFormSubmit}>
          {/* Row containing email input. */}
          <Row className="mb-4">
            <Col>
              <Form.Group>
                <Form.Label className="text-light">Email:</Form.Label>
                {/* Provide placeholder value and enable saving email of user. 
                Reset after save. */}
                <Form.Control
                  type="email"
                  placeholder="Email Address"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </Form.Group>
            </Col>
          </Row>
          {/* Row containing password input. */}
          <Row className="mb-4">
            <Col>
              <Form.Group>
                <Form.Label className="text-light">Password:</Form.Label>
                {/* Provide placeholder value and enable saving password of user. 
                Reset after save. */}
                <Form.Control
                  type="password"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mt-5 mb-2">
            <Col>
              {/* Rounded blue button with light text enabling the user to login.
               Submission will authenticate user once backend integration occurs. */}
              <Button
                variant="primary"
                className="rounded-pill text-light w-50"
                type="submit"
              >
                Sign In
              </Button>
            </Col>
          </Row>
        </Form>
      </Container>
    </>
  );
}

export default SignInForm;
