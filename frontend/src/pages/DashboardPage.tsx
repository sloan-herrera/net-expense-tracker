import Navbar from "../components/Navbar";

// Displays content of the dashboard page.
function Dashboard() {
  return (
    <>
      {/* Reusable navigation bar component. */}
      <Navbar />
      <h1 className="text-center text-light">Welcome to NetExpenseTracker</h1>
    </>
  );
}

export default Dashboard;
