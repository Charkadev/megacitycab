import { Link } from "react-router-dom";

const Help = () => {
  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <div className="max-w-3xl mx-auto bg-white p-6 shadow-md rounded-lg">
        <h1 className="text-3xl font-bold text-center mb-4">🚖 MegaCityCab Help Center</h1>
        <p className="text-gray-700 text-center mb-6">
          Need assistance? Find answers below or contact <strong>support@megacitycab.com</strong>.
        </p>

        {/* General Help */}
        <div className="mb-6">
          <h2 className="text-xl font-semibold mb-2">📌 General Help</h2>
          <p>
            MegaCityCab is a **fast and reliable cab booking system**. Whether you're a **customer** looking to book a ride or an **admin** managing drivers and reports, follow the guides below to get started.
          </p>
        </div>

        {/* Customer Guide */}
        <div className="mb-6">
          <h2 className="text-xl font-semibold mb-2">🚖 For Customers</h2>
          <ul className="list-disc ml-6">
            <li><strong>Register:</strong> Sign up with your name, email, and phone number.</li>
            <li><strong>Book a Ride:</strong> Enter pickup & drop-off locations and confirm.</li>
            <li><strong>Cancel a Ride:</strong> Go to <strong>"My Bookings"</strong> and cancel if needed.</li>
            <li><strong>View & Pay Bill:</strong> Check your ride fare after completing a trip.</li>
          </ul>
        </div>

        {/* Admin Guide */}
        <div className="mb-6">
          <h2 className="text-xl font-semibold mb-2">🏢 For Admins</h2>
          <ul className="list-disc ml-6">
            <li><strong>Manage Bookings:</strong> View, approve, or cancel rides.</li>
            <li><strong>Manage Drivers:</strong> Add, update, or remove driver details.</li>
            <li><strong>View Reports:</strong> Generate earnings & booking reports.</li>
          </ul>
        </div>

        {/* FAQs */}
        <div className="mb-6">
          <h2 className="text-xl font-semibold mb-2">❓ Frequently Asked Questions (FAQs)</h2>
          <details className="mb-2">
            <summary className="cursor-pointer font-semibold">How do I register?</summary>
            <p className="ml-4">Click on <Link to="/register" className="text-blue-500">Register</Link> and fill in your details.</p>
          </details>
          <details className="mb-2">
            <summary className="cursor-pointer font-semibold">How can I cancel a booking?</summary>
            <p className="ml-4">Go to <strong>"My Bookings"</strong> and click **Cancel**.</p>
          </details>
          <details className="mb-2">
            <summary className="cursor-pointer font-semibold">How do I contact customer support?</summary>
            <p className="ml-4">Email us at <strong>support@megacitycab.com</strong>.</p>
          </details>
        </div>

        {/* Back to Home */}
        <div className="text-center">
          <Link to="/" className="text-blue-500 underline">Go to Home</Link>
        </div>
      </div>
    </div>
  );
};

export default Help;
