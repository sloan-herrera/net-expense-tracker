import {
  Button,
  Card,
  Col,
  Container,
  Form,
  Row,
  Table,
} from "react-bootstrap";
import type { Expense } from "../types/types";
import { useState } from "react";
import { CATEGORIES, MONTHS } from "../constants/expenseConstants";

//TO DO: Replace any frontend exclusive functionality with API calls.

// Define object for React Properties.
type Properties = {
  expenses: Expense[];
  openUpdateModal: () => void;
};

// Expense table component to enable user to view their expenses.
function ExpenseTable({ expenses, openUpdateModal }: Properties) {
  // Set states for filtering values. Years are fetched from
  // expense data from BE in Iteration 3.
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedMonth, setSelectedMonth] = useState("");
  const [currentPage, setCurrentPage] = useState(1);

  // Resets filters.
  function clearFilters(): void {
    setSelectedCategory("");
    setSelectedMonth("");
    setCurrentPage(1);
  }

  // Routes to previous page of expenses if current page is greater than 1.
  // Backend pagination logic will be integrated in Iteration 3.
  function prevPage(): void {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  }

  // Routes to next page of expenses.
  // Backend pagination logic will be integrated in Iteration 3.
  function nextPage(): void {
    setCurrentPage(currentPage + 1);
  }

  return (
    <Container className="p-3 mt-5">
      <h3 className="text-center text-light mt-2">Expenses:</h3>
      {/* Expense filtering controls. */}
      <Row className="d-flex align-items-end mt-5 mb-5 gap-3">
        <Col md>
          {/* Filter by category dropdown selection. Will be handled by backend
          after integration. */}
          <Form.Group>
            <Form.Label className="text-light">Filter By Category:</Form.Label>
            <Form.Select
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              <option value="" disabled>
                All Categories
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
          {/* Filter by month dropdown selection. Will be handled by backend
          after integration. */}
          <Form.Group>
            <Form.Label className="text-light">Filter By Month:</Form.Label>
            <Form.Select
              value={selectedMonth}
              onChange={(e) => setSelectedMonth(e.target.value)}
            >
              <option value="" disabled>
                All Months
              </option>
              {/* Map months to stored constant values. */}
              {MONTHS.map((month) => (
                <option key={month} value={month}>
                  {month}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
        </Col>
        <Col md>
          {/* Filter by year dropdown selection. Year will populate with data
        fetched from backend. */}
          <Form.Group>
            <Form.Label className="text-light">Filter By Year:</Form.Label>
            <Form.Select>
              <option value="" disabled>
                All Years
              </option>
            </Form.Select>
          </Form.Group>
        </Col>
        <Col md>
          {/* Button to apply filters. Disabled until backend integration. */}
          <Button variant="secondary" className="rounded-pill w-100" disabled>
            Apply Filters
          </Button>
        </Col>
        <Col md>
          {/* Button to clear filters. Resets dropdown fields. Will reset query
        filtering after backend integration. */}
          <Button
            variant="secondary"
            className="rounded-pill w-100"
            onClick={clearFilters}
          >
            Clear Filters
          </Button>
        </Col>
      </Row>
      <Row className="mt-5 mb-5">
        <Col md>
          {/* Card layout for smaller screen sizes. */}
          <div className="d-md-none mb-3">
            {/* Map expenses based on index of array so that it displays as 
              cards. Later, this will be through API calls to backend. */}
            {expenses.map((expense, index) => (
              <Card key={index} className="mt-3">
                <Card.Body>
                  {/* Card title displays expense name. */}
                  <Card.Title className="text-center mb-3">
                    {expense.name}
                  </Card.Title>
                  <Card.Text className="mb-2">
                    <b>Amount: </b>${expense.amount}
                  </Card.Text>
                  <Card.Text className="mb-2">
                    <b>Category: </b>
                    {expense.category}
                  </Card.Text>
                  <Card.Text className="mb-2">
                    <b>Date: </b>
                    {expense.date}
                  </Card.Text>
                  {/* As shown in wireframes, there are two stacked buttons representing 
                  update and delete actions. Edit button opens update expense modal. Temporarily,
                  delete button is disabled until API integration occurs. */}
                  <div className="d-flex flex-column gap-3">
                    <Button
                      variant="warning"
                      size="sm"
                      onClick={openUpdateModal}
                      className="rounded-pill w-100"
                    >
                      Edit
                    </Button>
                    <Button
                      variant="danger"
                      size="sm"
                      className="rounded-pill w-100"
                      disabled
                    >
                      Delete
                    </Button>
                  </div>
                </Card.Body>
              </Card>
            ))}
          </div>

          {/* Table layout for larger screen sizes. */}
          <div className="d-none d-md-block">
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
                  update and delete actions. Edit button opens update expense modal. Temporarily,
                  delete button is disabled until API integration occurs. */}
                    <td className="d-flex justify-content-center gap-5">
                      <Button
                        variant="warning"
                        size="sm"
                        onClick={openUpdateModal}
                      >
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
          </div>
        </Col>
      </Row>
      {/* Pagination buttons. Placeholder logic is implemented currently, but will be updated
      during Iteration 3 with backend integration. */}
      <Row className="d-flex align-items-center mb-5 gap-2">
        <Col md>
          {/* Previous button. Button is disabled if current page is 1. */}
          <Button
            variant="secondary"
            className="rounded-pill w-100"
            onClick={prevPage}
            disabled={currentPage === 1}
          >
            Previous
          </Button>
        </Col>
        <Col md>
          <p className="text-light text-center">Page {currentPage}</p>
        </Col>
        <Col md>
          {/* Next button. */}
          <Button
            variant="secondary"
            className="rounded-pill w-100"
            onClick={nextPage}
          >
            Next
          </Button>
        </Col>
      </Row>
    </Container>
  );
}

export default ExpenseTable;
