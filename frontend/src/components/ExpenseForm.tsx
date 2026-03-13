import { useState } from "react";
import { Button, Col, Container, Form, InputGroup, Row } from "react-bootstrap";
import type { Expense } from "../types/types";
import { CATEGORIES } from "../constants/expenseConstants";

//TO DO: Replace any frontend exclusive functionality with API calls.

// Define object for React Properties.
type Properties = {
  createExpense: (expense: Expense) => void;
};

// Expense form component for creating expenses.
function ExpenseForm({ createExpense }: Properties) {
  // Set states to enable saving of values.
  const [name, setName] = useState("");
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("");
  const [date, setDate] = useState("");

  // Formats the amount, ensuring it is always number and displays two decimal places.
  const formatAmount = (): void => {
    if (amount !== "" && !isNaN(Number(amount))) {
      setAmount(Number(amount).toFixed(2));
    }
  };

  // Handles form submission by preventing the page from refreshing, creating
  // a new expense based on form input, and passing it to dashboard page.
  function handleSubmit(e: React.SyntheticEvent<HTMLFormElement>) {
    e.preventDefault();

    // Check if values are empty. If they are, prevent saving.
    if (name === "" || amount === "" || category === "" || date === "") {
      return;
    }

    const newExpense = {
      name,
      amount,
      category,
      date,
    };

    createExpense(newExpense);

    // Reset after expense is created.
    setName("");
    setAmount("");
    setCategory("");
    setDate("");
  }

  return (
    <Container className="p-3">
      <h3 className="text-center text-light mt-2">Create an expense:</h3>
      <Form onSubmit={handleSubmit}>
        {/* Grid layout implemented for form. */}
        <Row className="mt-4 mb-5">
          {/* Col set to md ensure responsive layout. */}
          <Col md>
            <Form.Group className="w-100">
              <Form.Label className="text-light">Name:</Form.Label>
              {/* Provide placeholder value and enable saving name of expense. 
              Reset after save. */}
              <Form.Control
                type="text"
                placeholder="Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </Form.Group>
          </Col>
          <Col md>
            <Form.Group className="w-100">
              <Form.Label className="text-light">Amount:</Form.Label>
              <InputGroup>
                {/* Display dollar amount. Other currencies are not supported. */}
                <InputGroup.Text>$</InputGroup.Text>
                {/* Ensures a minimum value of zero, that the amount changes to the
                proper format, and is saved. */}
                <Form.Control
                  type="number"
                  step="0.01"
                  min="0"
                  placeholder="Amount"
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                  onBlur={formatAmount}
                />
              </InputGroup>
            </Form.Group>
          </Col>
        </Row>
        <Row className="mt-4 mb-5">
          <Col md>
            <Form.Group className="w-100">
              <Form.Label className="text-light">Category:</Form.Label>
              {/* Enable saving category of expense. Reset after save. */}
              <Form.Select
                value={category}
                onChange={(e) => setCategory(e.target.value)}
              >
                {/* Default value is disabled and will never be saved as a category. */}
                <option value="" disabled>
                  Category
                </option>
                {/* Map categories to stored constant values. */}
                {CATEGORIES.map((category) => (
                  <option key={category} value={category}>
                    {category}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </Col>
          <Col md>
            <Form.Group className="w-100">
              <Form.Label className="text-light">Date:</Form.Label>
              {/* Enable saving date of expense. Reset after save. */}
              <Form.Control
                type="date"
                value={date}
                onChange={(e) => setDate(e.target.value)}
              />
            </Form.Group>
          </Col>
        </Row>
        <Row className="mt-4 mb-3">
          {/* Center column in row using flexbox settings. */}
          <Col md className="d-flex justify-content-center">
            {/* Button submits information and sends to backend via POST
                HTTP method. For now, it just generates the expense in the table. */}
            <Button
              variant="primary"
              className="rounded-pill text-light w-50"
              type="submit"
            >
              Create Expense
            </Button>
          </Col>
        </Row>
      </Form>
    </Container>
  );
}

export default ExpenseForm;
