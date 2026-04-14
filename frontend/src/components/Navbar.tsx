// Importing Navbar as BootstrapNavbar removes naming conflict error.
import { Navbar as BootstrapNavbar } from "react-bootstrap";
import { Container, Nav } from "react-bootstrap";
import { NavLink, useNavigate } from "react-router-dom";
import { logoutUser } from "../services/authService";

// Define object for React Properties
type Properties = {
  setIsAuthenticated: (isAuthenticated: boolean) => void;
};

// Navigation bar component for dashboard and contact pages of NetExpenseTracker.
function Navbar({ setIsAuthenticated }: Properties) {
  // Used to navigate to other pages.
  const navigate = useNavigate();

  // Handles logout by calling backend logout endpoint.
  const handleLogout = async () => {
    // Attempt to call authService logoutUser function.
    try {
      await logoutUser();

      // Clear authentication state on successful logout.
      setIsAuthenticated(false);

      // Redirect to sign-in page on logout.
      navigate("/");
      // If an error occurs, catch it, return the message, and alert user.
    } catch (error) {
      console.error("Error logging user out", error);
      alert("Logout Failed.");
    }
  };

  return (
    <BootstrapNavbar bg="dark" variant="dark">
      {/* Navbar container changes to column layout on smaller screens. */}
      <Container
        fluid
        className="d-flex flex-column flex-md-row align-items-center"
      >
        {/* Click on brand label redirects to dashboard. Margins adjusted for screen size. */}
        <BootstrapNavbar.Brand
          as={NavLink}
          to="/dashboard"
          className="text-center text-decoration-none mb-2 mb-md-0"
        >
          NetExpenseTracker
        </BootstrapNavbar.Brand>
        {/* Margin start auto is used to push link items to the right of brand in desktop layout.
        Changes to column layout on smaller screens. */}
        <Nav className="d-flex flex-column flex-md-row align-items-center ms-md-auto gap-2">
          {/* NavLink from React Router is used for frontend routing and active link highlighting. */}
          <Nav.Link as={NavLink} to="/dashboard">
            Dashboard
          </Nav.Link>
          <Nav.Link as={NavLink} to="/contact">
            Contact
          </Nav.Link>
          <Nav.Link
            as={NavLink}
            to="/"
            onClick={async (e) => {
              e.preventDefault(); // Prevent instant redirect before logout completes.
              await handleLogout();
            }}
          >
            Logout
          </Nav.Link>
        </Nav>
      </Container>
    </BootstrapNavbar>
  );
}

export default Navbar;
