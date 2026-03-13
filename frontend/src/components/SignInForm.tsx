import { Button, Col, Container, Form, Row } from "react-bootstrap";

// Define object for React Properties.
type Properties = {
  handleSubmit: () => void;
};

// Form for the sign-in page.
function SignInForm({ handleSubmit }: Properties) {
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
        <Form onSubmit={handleSubmit}>
          {/* Row containing email input. */}
          <Row className="mb-4">
            <Col>
              <Form.Group>
                <Form.Label className="text-light">Email:</Form.Label>
                <Form.Control type="email" placeholder="Email Address" />
              </Form.Group>
            </Col>
          </Row>
          {/* Row containing password input. */}
          <Row className="mb-4">
            <Col>
              <Form.Group>
                <Form.Label className="text-light">Password:</Form.Label>
                <Form.Control type="password" placeholder="Password" />
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
