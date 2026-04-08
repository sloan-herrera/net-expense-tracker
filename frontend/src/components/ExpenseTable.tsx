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
import { CATEGORIES, MONTHS, YEARS } from "../constants/constants";

// Define object for React Properties.
type Properties = {
  expenses: Expense[];
  selectedCategory: string;
  selectedMonth: number | undefined;
  selectedYear: number | undefined;
  currentPage: number;
  totalPages: number;
  openUpdateModal: (expense: Expense) => void;
  deleteExpense: (expenseId: string) => Promise<void>;
  setSelectedCategory: (category: string) => void;
  setSelectedMonth: (month: number | undefined) => void;
  setSelectedYear: (year: number | undefined) => void;
  applyFilters: () => Promise<void>;
  clearFilters: () => Promise<void>;
  prevPage: () => Promise<void>;
  nextPage: () => Promise<void>;
};

// Expense table component to enable user to view their expenses.
function ExpenseTable({
  expenses,
  selectedCategory,
  selectedMonth,
  selectedYear,
  currentPage,
  totalPages,
  openUpdateModal,
  deleteExpense,
  setSelectedCategory,
  setSelectedMonth,
  setSelectedYear,
  applyFilters,
  clearFilters,
  prevPage,
  nextPage,
}: Properties) {
  return (
    <Container className="p-3 mt-5">
      <h3 className="text-center text-light mt-2">Expenses:</h3>
      {/* Expense filtering controls. */}
      <Row className="d-flex align-items-end mt-5 mb-5 gap-3">
        <Col md>
          {/* Filter by category dropdown selection.*/}
          <Form.Group>
            <Form.Label className="text-light">Filter By Category:</Form.Label>
            <Form.Select
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              <option value="">All Categories</option>
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
          {/* Filter by month dropdown selection. */}
          <Form.Group>
            <Form.Label className="text-light">Filter By Month:</Form.Label>
            {/* Selected month needs to be in numerical format. It can also be
            undefined to enable no filtering. */}
            <Form.Select
              value={selectedMonth ?? ""}
              onChange={(e) => {
                // Convert month from string to numerical format. If the value is
                // empty, set as undefined.
                if (e.target.value === "") {
                  setSelectedMonth(undefined);
                } else {
                  setSelectedMonth(Number(e.target.value));
                }
              }}
            >
              <option value="">All Months</option>
              {/* Map months to stored constant values. Index value enables
              the month to be stored in numerical format. */}
              {MONTHS.map((month, index) => (
                <option key={month} value={index + 1}>
                  {month}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
        </Col>
        <Col md>
          {/* Filter by year dropdown selection. */}
          <Form.Group>
            <Form.Label className="text-light">Filter By Year:</Form.Label>
            {/* Selected year needs to be in numerical format. It can also be
            undefined to enable no filtering. */}
            <Form.Select
              value={selectedYear ?? ""}
              onChange={(e) => {
                // Convert year from string to numerical format. If the value is
                // empty, set as undefined.
                if (e.target.value === "") {
                  setSelectedYear(undefined);
                } else {
                  setSelectedYear(Number(e.target.value));
                }
              }}
            >
              <option value="">All Years</option>
              {/* Map years to stored constant values. */}
              {YEARS.map((year) => (
                <option key={year} value={year}>
                  {year}
                </option>
              ))}
            </Form.Select>
          </Form.Group>
        </Col>
        <Col md>
          {/* Button to apply filters. */}
          <Button
            variant="secondary"
            className="rounded-pill w-100"
            onClick={applyFilters}
          >
            Apply Filters
          </Button>
        </Col>
        <Col md>
          {/* Button to clear filters. Resets dropdown fields. */}
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
            {/* If there are no expenses to display, show message. */}
            {expenses.length === 0 && (
              <p className="text-light text-center">No expenses found.</p>
            )}
            {/* If there are expenses to display, show in cards. */}
            {expenses.length > 0 &&
              // Map retrieved expenses to card layout.
              expenses.map((expense) => (
                <Card key={expense.expenseId} className="mt-3">
                  <Card.Body>
                    {/* Card title displays expense name. */}
                    <Card.Title className="text-center mb-3">
                      {expense.name}
                    </Card.Title>
                    <Card.Text className="mb-2">
                      <b>Amount: </b>${expense.amount.toFixed(2)}
                    </Card.Text>
                    <Card.Text className="mb-2">
                      <b>Category: </b>
                      {expense.category}
                    </Card.Text>
                    <Card.Text className="mb-2">
                      <b>Date: </b>
                      {expense.date}
                    </Card.Text>
                    {/* Stacked buttons provide update and delete actions on 
                  smaller screens. */}
                    <div className="d-flex flex-column gap-3">
                      <Button
                        variant="warning"
                        size="sm"
                        className="rounded-pill w-100"
                        onClick={() => openUpdateModal(expense)}
                      >
                        Edit
                      </Button>
                      <Button
                        variant="danger"
                        size="sm"
                        className="rounded-pill w-100"
                        onClick={() => deleteExpense(expense.expenseId)}
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
            {/* If there are no expenses to display, show message. */}
            {expenses.length === 0 && (
              <p className="text-light text-center">No expenses found.</p>
            )}
            {/* If there are expenses to display, show in table. */}
            {expenses.length > 0 && (
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
                  {/* Map retrieved expenses to table rows. */}
                  {expenses.map((expense) => (
                    <tr key={expense.expenseId}>
                      <td>{expense.name}</td>
                      <td>${expense.amount.toFixed(2)}</td>
                      <td>{expense.category}</td>
                      <td>{expense.date}</td>
                      {/* Action buttons provide update and delete actions on 
                    larger screens. */}
                      <td className="d-flex justify-content-center gap-5">
                        <Button
                          variant="warning"
                          size="sm"
                          className="rounded-pill w-100"
                          onClick={() => openUpdateModal(expense)}
                        >
                          Edit
                        </Button>
                        <Button
                          variant="danger"
                          size="sm"
                          className="rounded-pill w-100"
                          onClick={() => deleteExpense(expense.expenseId)}
                        >
                          Delete
                        </Button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            )}
          </div>
        </Col>
      </Row>
      {/* Pagination buttons to navigate through expense pages. */}
      <Row className="d-flex align-items-center mb-5 gap-2">
        <Col md>
          {/* Previous button. Button is disabled if current page is 0 (first page). */}
          <Button
            variant="secondary"
            className="rounded-pill w-100"
            onClick={prevPage}
            disabled={currentPage === 0}
          >
            Previous
          </Button>
        </Col>
        <Col md>
          <p className="text-light text-center">Page {currentPage + 1}</p>
        </Col>
        <Col md>
          {/* Next button. Button is disabled if the current page is the last one 
          or if there is only one page of expenses.*/}
          <Button
            variant="secondary"
            className="rounded-pill w-100"
            onClick={nextPage}
            disabled={totalPages === 0 || currentPage >= totalPages - 1}
          >
            Next
          </Button>
        </Col>
      </Row>
    </Container>
  );
}

export default ExpenseTable;
