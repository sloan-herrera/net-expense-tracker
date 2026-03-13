import ContactForm from "../components/ContactForm";
import Navbar from "../components/Navbar";

// Displays content of the contact page.
function Contact() {
  return (
    <>
      {/* Reusable navigation bar component. */}
      <Navbar />
      <h1 className="text-center text-light mt-2">Contact Support</h1>
      <ContactForm />
    </>
  );
}

export default Contact;
