// Importing Navbar as BootstrapNavbar removes naming conflict error.
import { Navbar as BootstrapNavbar } from "react-bootstrap";
import { Container, Nav } from "react-bootstrap";
import { NavLink } from "react-router-dom";

// Navigation bar component for dashboard and contact pages of NetExpenseTracker.
function Navbar() {
  return (
    <BootstrapNavbar bg="dark" variant="dark">
      {/* Navbar container is fluid for screen size, although true responsiveness
        will be implemented later. */}
      <Container fluid>
        {/* Click on brand label redirects to dashboard. */}
        <BootstrapNavbar.Brand
          as={NavLink}
          to="/dashboard"
          className="text-decoration-none"
        >
          NetExpenseTracker
        </BootstrapNavbar.Brand>
        {/* Margin start auto is used to push link items to the right of brand. */}
        <Nav className="ms-auto">
          {/* NavLink from React Router is used for frontend routing and active link highlighting. */}
          <Nav.Link as={NavLink} to="/dashboard">
            Dashboard
          </Nav.Link>
          <Nav.Link as={NavLink} to="/contact">
            Contact
          </Nav.Link>
          <Nav.Link as={NavLink} to="/">
            Logout
          </Nav.Link>
        </Nav>
      </Container>
    </BootstrapNavbar>
  );
}

export default Navbar;
