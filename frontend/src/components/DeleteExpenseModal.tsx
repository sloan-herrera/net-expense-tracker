import { Button, Col, Container, Modal, Row } from "react-bootstrap";
import type { Expense } from "../types/types";

// Define object for React Properties.
type Properties = {
  show: boolean;
  selectedExpense: Expense | null;
  handleClose: () => void;
  deleteExpense: (expenseId: string) => Promise<void>;
};

// Delete expense modal component for confirmation for deleting expenses.
function DeleteExpenseModal({
  show,
  selectedExpense,
  handleClose,
  deleteExpense,
}: Properties) {
  // When the "Delete" button is clicked, call deleteExpense API with the selected expense ID.
  async function handleDelete() {
    // If no expense is selected, return nothing.
    if (!selectedExpense) {
      return;
    }

    await deleteExpense(selectedExpense.expenseId);
  }

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton className="bg-dark text-light">
        <Modal.Title>Delete Expense</Modal.Title>
      </Modal.Header>
      <Modal.Body className="bg-dark text-light">
        <Container className="p-3">
          {/* Grid layout implemented for modal. */}
          <Row className="mt-1 mb-1">
            {/* Col set to md ensure responsive layout. */}
            <Col md>
              <p className="text-center">
                Are you sure you would like to delete "{selectedExpense?.name}"?
              </p>
            </Col>
          </Row>
          <Row className="mt-1 mb-1 gap-3">
            <Col md>
              {/* Button cancels the deletion by closing modal. */}
              <Button
                variant="primary"
                size="sm"
                className="rounded-pill text-light w-100"
                onClick={handleClose}
              >
                Cancel
              </Button>
            </Col>
            <Col md>
              {/* Button calls DELETE HTTP method from backend to remove expense. */}
              <Button
                variant="danger"
                size="sm"
                className="rounded-pill text-light w-100"
                onClick={handleDelete}
              >
                Delete
              </Button>
            </Col>
          </Row>
        </Container>
      </Modal.Body>
    </Modal>
  );
}

export default DeleteExpenseModal;
