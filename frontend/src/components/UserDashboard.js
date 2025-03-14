import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

function UserDashboard() {
  const navigate = useNavigate();
  const [bookings, setBookings] = useState([]);
  const [billingHistory, setBillingHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedBooking, setSelectedBooking] = useState(null);

  useEffect(() => {
    const fetchBookingsAndBilling = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          console.error("🚨 No auth token found");
          return;
        }

        // ✅ Fetch user bookings with authentication
        const response = await api.get(`/bookings/user`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setBookings(response.data || []);

        // ✅ Fetch billing history with authentication
        const billingResponse = await api.get("/billing/user", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setBillingHistory(billingResponse.data || []);
      } catch (error) {
        console.error("🚨 Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchBookingsAndBilling();
  }, []);

  // ✅ Handle booking cancellation
  const handleCancelBooking = async (id) => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("You must be logged in to cancel a booking.");
        return;
      }

      const response = await api.put(
        `/bookings/cancel/${id}`,
        {},
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (response.status === 200) {
        setBookings((prev) =>
          prev.map((b) => (b.id === id ? { ...b, status: "CANCELLED" } : b))
        );
        alert("Booking cancelled successfully.");
      } else {
        throw new Error("Cancellation failed.");
      }
    } catch (error) {
      console.error("🚨 Error cancelling booking:", error.response?.data || error);
      alert("Failed to cancel booking. Please try again.");
    }
  };

  // ✅ Generate PDF for billing details
  const generatePDF = () => {
    const doc = new jsPDF();
    doc.text("Billing History", 20, 10);

    const tableData = billingHistory.map((bill) => [
      bill.bookingId,
      `$${bill.fare.toFixed(2)}`,
      `$${bill.tax.toFixed(2)}`,
      `$${bill.totalAmount.toFixed(2)}`,
      new Date(bill.timestamp).toLocaleString(),
    ]);

    autoTable(doc, {
      head: [["Booking ID", "Fare", "Tax", "Total", "Date"]],
      body: tableData,
    });

    doc.save("Billing_History.pdf");
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold">Your Dashboard</h1>

      {/* ✅ "Create Booking" Button */}
      <button
        onClick={() => navigate("/booking")}
        className="mt-4 bg-blue-500 text-white px-4 py-2 rounded"
      >
        Create Booking
      </button>

      {/* ✅ Pending Bookings */}
      <h2 className="text-xl font-semibold mt-4">Your Bookings (Pending)</h2>
      {loading ? (
        <p>Loading bookings...</p>
      ) : bookings.filter((booking) => booking.status === "PENDING").length === 0 ? (
        <p>No pending bookings.</p>
      ) : (
        <ul className="mt-2">
          {bookings
            .filter((booking) => booking.status === "PENDING")
            .map((booking, index) => (
              <li
                key={booking.id || index} // ✅ Ensures unique keys
                className="p-2 border rounded mb-2 flex justify-between items-center"
              >
                🚖 {booking.pickupLocation} → {booking.dropoffLocation}
                <span className="text-yellow-500">PENDING</span>
                <span className="ml-2">- Fare: ${booking.fare?.toFixed(2) || "N/A"}</span>

                <button
                  onClick={() => handleCancelBooking(booking.id)}
                  className="ml-4 bg-red-500 text-white px-3 py-1 rounded"
                >
                  Cancel
                </button>

                <button
                  onClick={() => setSelectedBooking(booking)}
                  className="ml-2 bg-blue-500 text-white px-3 py-1 rounded"
                >
                  View Details
                </button>
              </li>
            ))}
        </ul>
      )}

      {/* ✅ Booking History */}
      <h2 className="text-xl font-semibold mt-6">Booking History</h2>
      {loading ? (
        <p>Loading history...</p>
      ) : bookings.filter((booking) => booking.status !== "PENDING").length === 0 ? (
        <p>No previous bookings.</p>
      ) : (
        <ul className="mt-2">
          {bookings
            .filter((booking) => booking.status !== "PENDING")
            .map((booking) => (
              <li key={booking.id} className="p-2 border rounded mb-2">
                🚖 {booking.pickupLocation} → {booking.dropoffLocation}
                <span
                  className={`ml-2 ${
                    booking.status === "COMPLETED" ? "text-green-500" : "text-red-500"
                  }`}
                >
                  {booking.status}
                </span>
                {booking.status === "COMPLETED" && (
                  <>
                    <span className="ml-2">- Fare: ${booking.fare?.toFixed(2) || "N/A"}</span>
                    <p className="text-sm text-gray-500">
                      Driver: {booking.driverDetails?.name} | Phone: {booking.driverDetails?.phone}
                    </p>
                  </>
                )}
              </li>
            ))}
        </ul>
      )}

      {/* ✅ Billing History */}
      <h2 className="text-xl font-semibold mt-6">Billing History</h2>
      {loading ? (
        <p>Loading billing history...</p>
      ) : billingHistory.length === 0 ? (
        <p>No billing records found.</p>
      ) : (
        <ul className="mt-2">
          {billingHistory.map((bill) => (
            <li key={bill.bookingId} className="p-2 border rounded mb-2">
              🧾 Booking ID: {bill.bookingId} - Fare: ${bill.fare.toFixed(2)} | 
              Tax: ${bill.tax.toFixed(2)} | Total: ${bill.totalAmount.toFixed(2)}
            </li>
          ))}
        </ul>
      )}

      {billingHistory.length > 0 && (
        <button
          onClick={generatePDF}
          className="mt-4 bg-green-500 text-white px-4 py-2 rounded"
        >
          Download Billing History (PDF)
        </button>
      )}

      {/* ✅ Booking Details Modal */}
      {selectedBooking && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded shadow-lg max-w-md">
            <h2 className="text-xl font-semibold mb-4">Booking Details</h2>
            <p><strong>Pickup:</strong> {selectedBooking.pickupLocation}</p>
            <p><strong>Dropoff:</strong> {selectedBooking.dropoffLocation}</p>
            <p><strong>Status:</strong> {selectedBooking.status}</p>
            <p><strong>Fare:</strong> ${selectedBooking.fare?.toFixed(2) || "N/A"}</p>

            <button
              onClick={() => setSelectedBooking(null)}
              className="mt-4 bg-red-500 text-white px-4 py-2 rounded"
            >
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default UserDashboard;
