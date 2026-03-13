import { useState } from "react";
import { Button, Col, Container, Form, Row } from "react-bootstrap";

// Form for the contact page.
function ContactForm() {
  const [message, setMessage] = useState("");

  // Handles form submission by preventing the page from refreshing and
  // sending the message to the support email address.
  function handleSubmit(e: React.SyntheticEvent<HTMLFormElement>) {
    e.preventDefault();

    // Check if message is empty. If it is, prevent sending.
    if (message === "") {
      return;
    }

    // Open user's email client and send to support email address.
    // Placeholder email used for now, as support email address has not
    // been created.
    // TO DO: Create support email address and replace placeholder.
    window.location.href = "mailto:netexpensesupport@test.com?body=" + message;

    // Reset message field.
    setMessage("");
  }

  return (
    <>
      {/* Container for contact form. */}
      <Container className="text-center mt-5 p-4">
        {/* Form header. */}
        <Row className="mb-4">
          <Col>
            <h3 className="text-light">Send a Message</h3>
          </Col>
        </Row>
        {/* Form body. */}
        <Form onSubmit={handleSubmit}>
          {/* Row containing message to be sent. */}
          <Row className="mb-2">
            <Col>
              <Form.Group>
                <Form.Label className="text-light">Message:</Form.Label>
                {/* Input type is textarea with eight rows. */}
                <Form.Control
                  as="textarea"
                  rows={8}
                  placeholder="Message"
                  value={message}
                  onChange={(e) => setMessage(e.target.value)}
                />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mt-5 mb-2">
            <Col>
              {/* Rounded blue button with light text to send message. */}
              <Button
                variant="primary"
                className="rounded-pill text-light w-50"
                type="submit"
              >
                Send
              </Button>
            </Col>
          </Row>
        </Form>
      </Container>
    </>
  );
}

export default ContactForm;
