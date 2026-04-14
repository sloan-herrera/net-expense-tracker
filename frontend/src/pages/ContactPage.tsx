import ContactForm from "../components/ContactForm";
import Navbar from "../components/Navbar";

// Define object for React Properties
type Properties = {
  setIsAuthenticated: (isAuthenticated: boolean) => void;
};

// Displays content of the contact page.
function Contact({ setIsAuthenticated }: Properties) {
  return (
    <>
      {/* Reusable navigation bar component. */}
      <Navbar setIsAuthenticated={setIsAuthenticated} />
      <h1 className="text-center text-light mt-2">Contact Support</h1>
      <ContactForm />
    </>
  );
}

export default Contact;
