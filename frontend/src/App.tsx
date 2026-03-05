import { Container } from "react-bootstrap";
import { Row } from "react-bootstrap";
import { Col } from "react-bootstrap";
import { Button } from "react-bootstrap";

function App() {
  return (
    // Placeholder code to test if React Bootstrap works.
    <>
      <Container fluid>
        <Row className="text-center">
          <Col>
            <h1>Welcome to NetExpenseTracker!</h1>
          </Col>
        </Row>
        <Row className="text-center">
          <Col>
            <Button variant="outline-primary">Test Button 1</Button>
          </Col>
          <Col>
            <Button variant="outline-primary">Test Button 2</Button>
          </Col>
          <Col>
            <Button variant="outline-primary">Test Button 3</Button>
          </Col>
        </Row>
      </Container>
    </>
  )
}

export default App
