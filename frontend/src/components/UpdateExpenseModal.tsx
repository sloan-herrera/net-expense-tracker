import {
  Button,
  Col,
  Container,
  Form,
  InputGroup,
  Modal,
  Row,
} from "react-bootstrap";
import { useState } from "react";
import type { Expense, ExpenseRequest } from "../types/types";

// Define object for React Properties.
type Properties = {
  show: boolean;
  selectedExpense: Expense | null;
  handleClose: () => void;
  updateExpense: (expenseData: ExpenseRequest) => Promise<void>;
};

// Update expense modal component for updating expenses.
function UpdateExpenseModal({
  show,
  selectedExpense,
  handleClose,
  updateExpense,
}: Properties) {
  // Set states to enable saving of values. Includes default values.
  const [name, setName] = useState(selectedExpense?.name ?? "");
  const [amount, setAmount] = useState(
    selectedExpense?.amount.toFixed(2) ?? "",
  );
  const [category, setCategory] = useState(selectedExpense?.category ?? "");
  const [date, setDate] = useState(selectedExpense?.date ?? "");

  // Formats the amount, ensuring it is always number and displays two decimal places.
  const formatAmount = (): void => {
    if (amount !== "" && !isNaN(Number(amount))) {
      setAmount(Number(amount).toFixed(2));
    }
  };

  // Handles form submission by preventing the page from refreshing and updating the
  // expense through the backend.
  async function handleSubmit(e: React.SyntheticEvent<HTMLFormElement>) {
    e.preventDefault();

    // Check if values are empty. If they are, prevent saving.
    if (name === "" || amount === "" || category === "" || date === "") {
      return;
    }

    // Place updated expense data in updatedExpense object.
    const updatedExpense = {
      name,
      amount: Number(amount),
      category,
      date,
    };

    // Pass updatedExpense object to dashboard to send to backend.
    await updateExpense(updatedExpense);
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton className="bg-dark text-light">
        <Modal.Title>Update Expense</Modal.Title>
      </Modal.Header>
      <Modal.Body className="bg-dark text-light">
        <Container className="p-3">
          <Form onSubmit={handleSubmit}>
            {/* Grid layout implemented for form. */}
            <Row className="mt-4 mb-5">
              {/* Col set to md ensure responsive layout. */}
              <Col md>
                <Form.Group className="w-100">
                  <Form.Label className="text-light">Name:</Form.Label>
                  {/* Provide placeholder value and enable updating name of expense. */}
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
                  {/* Enable updating category of expense. */}
                  <Form.Select
                    value={category}
                    onChange={(e) => setCategory(e.target.value)}
                  >
                    {/* Default value is disabled and will never be saved as a category. */}
                    <option value="" disabled>
                      Category
                    </option>
                    <option value="Housing">Housing</option>
                    <option value="Utilities">Utilities</option>
                    <option value="Food">Food</option>
                    <option value="Transportation">Transportation</option>
                    <option value="Health">Health</option>
                    <option value="Shopping">Shopping</option>
                    <option value="Entertainment">Entertainment</option>
                    <option value="Subscriptions">Subscriptions</option>
                    <option value="Miscellaneous">Miscellaneous</option>
                  </Form.Select>
                </Form.Group>
              </Col>
              <Col md>
                <Form.Group className="w-100">
                  <Form.Label className="text-light">Date:</Form.Label>
                  {/* Enable updating date of expense. */}
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
                {/* Button submits information and sends to backend via PUT
                        HTTP method. For now, it just closes the modal. */}
                <Button
                  variant="primary"
                  className="rounded-pill text-light w-50"
                  type="submit"
                >
                  Update Expense
                </Button>
              </Col>
            </Row>
          </Form>
        </Container>
      </Modal.Body>
    </Modal>
  );
}

export default UpdateExpenseModal;
