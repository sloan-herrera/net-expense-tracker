import { Button, Col, Container, Row, Table } from "react-bootstrap";
import type { Expense } from "../types/types";

//TO DO: Replace any frontend exclusive functionality with API calls.

// Define object for React Properties.
type Properties = {
  expenses: Expense[];
};

// Expense table component to enable user to view their expenses.
function ExpenseTable({ expenses }: Properties) {
  return (
    <Container className="p-3 mt-5">
      <h3 className="text-center text-light mt-2">Expenses:</h3>
      <Row className="mt-4 mb-5">
        <Col md>
          <Table striped bordered hover variant="light">
            {/* Table header provides labeling for columns. */}
            <thead>
              <tr>
                <th>Name</th>
                <th>Amount</th>
                <th>Category</th>
                <th>Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {/* Map expenses based on index of array so that it displays in 
              the table. Later, this will be through API calls to backend. */}
              {expenses.map((expense, index) => (
                <tr key={index}>
                  <td>{expense.name}</td>
                  <td>${expense.amount}</td>
                  <td>{expense.category}</td>
                  <td>{expense.date}</td>
                  {/* As shown in wireframes, there are two centered buttons representing 
                  update and delete actions. Temporarily disabled, as they don't
                  function yet. */}
                  <td className="d-flex justify-content-center gap-5">
                    <Button variant="secondary" size="sm" disabled>
                      Edit
                    </Button>
                    <Button variant="danger" size="sm" disabled>
                      Delete
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>
    </Container>
  );
}

export default ExpenseTable;
