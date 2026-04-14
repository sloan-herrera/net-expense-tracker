import { Navigate } from "react-router-dom";

// Define object for React Properties
type Properties = {
  isAuthenticated: boolean;
  isLoading: boolean;
  children: React.ReactNode;
};

// Protects routes from unauthorized access in web browser.
function ProtectedRoute({ isAuthenticated, isLoading, children }: Properties) {
  // Prevent redirect until authentication check is complete.
  if (isLoading) {
    return null;
  }

  // If the user is not authenticated, return to the sign-in page.
  if (!isAuthenticated) {
    return <Navigate to="/" replace />;
  }

  // If the user is authenticated, allow access to protected routes.
  return <>{children}</>;
}

export default ProtectedRoute;
