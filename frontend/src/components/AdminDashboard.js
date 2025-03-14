import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

const AdminDashboard = () => {
  const navigate = useNavigate();
  const [totalEarnings, setTotalEarnings] = useState(0);
  const [bookingSummary, setBookingSummary] = useState({ completed: 0, pending: 0, cancelled: 0 });
  const [bookings, setBookings] = useState([]);
  const [users, setUsers] = useState([]);
  const [drivers, setDrivers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAdminData = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          console.error("🚨 No auth token found");
          return;
        }

        const headers = { Authorization: `Bearer ${token}` };

        // Fetch total earnings
        const earningsRes = await api.get("/admin/reports/total-earnings", { headers });
        setTotalEarnings(earningsRes.data);

        // Fetch booking summary
        const summaryRes = await api.get("/admin/reports/booking-summary", { headers });
        setBookingSummary(summaryRes.data);

        // Fetch all bookings
        const bookingsRes = await api.get("/admin/all-bookings", { headers });
        setBookings(bookingsRes.data || []);

        // Fetch all users
        const usersRes = await api.get("/admin/all-users", { headers });
        setUsers(usersRes.data || []);

        // Fetch all drivers
        const driversRes = await api.get("/drivers/all", { headers });
        setDrivers(driversRes.data || []);
      } catch (error) {
        console.error("🚨 Error fetching admin data", error);
      } finally {
        setLoading(false);
      }
    };

    fetchAdminData();
  }, []);

  // ✅ Mark Booking as Completed
  const markBookingAsCompleted = async (id) => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found");
        return;
      }

      await api.put(`/admin/mark-booking-complete/${id}`, {}, { headers: { Authorization: `Bearer ${token}` } });
      setBookings((prev) =>
        prev.map((b) => (b.id === id ? { ...b, status: "COMPLETED" } : b))
      );
      alert("Booking marked as completed.");
    } catch (error) {
      console.error("🚨 Error completing booking:", error);
      alert("Failed to mark booking as completed.");
    }
  };

  // ✅ Generate PDF for Booking Reports
  const exportPDF = () => {
    const doc = new jsPDF();
    doc.text("Admin Booking Reports", 20, 10);

    const tableData = bookings.map((booking) => [
      booking.id,
      booking.user,
      booking.pickupLocation,
      booking.dropoffLocation,
      booking.status,
      `$${booking.fare.toFixed(2)}`,
    ]);

    autoTable(doc, {
      head: [["Booking ID", "User", "Pickup", "Dropoff", "Status", "Fare"]],
      body: tableData,
    });

    doc.save("Admin_Booking_Reports.pdf");
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold">Admin Panel</h2>
      <p>Manage bookings, users, drivers, cars, and reports.</p>

      {/* 📊 System Reports */}
      <div className="mt-6 bg-white p-4 shadow rounded">
        <h3 className="text-lg font-bold">📊 System Reports</h3>
        <p className="text-gray-700">Total Earnings: <strong>${totalEarnings}</strong></p>
        <p className="mt-2"><strong>✅ Completed:</strong> {bookingSummary.completed}</p>
        <p><strong>🟡 Pending:</strong> {bookingSummary.pending}</p>
        <p><strong>❌ Cancelled:</strong> {bookingSummary.cancelled}</p>
      </div>

      {/* 🚗 View All Bookings */}
      <div className="mt-6 bg-white p-4 shadow rounded">
        <h3 className="text-lg font-bold">🚗 All Bookings</h3>
        {loading ? (
          <p>Loading bookings...</p>
        ) : bookings.length === 0 ? (
          <p>No bookings available.</p>
        ) : (
          <ul className="mt-2">
            {bookings.map((booking) => (
              <li key={booking.id} className="p-2 border rounded mb-2 flex justify-between items-center">
                {booking.pickupLocation} → {booking.dropoffLocation} | 
                <span className={`ml-2 ${booking.status === "COMPLETED" ? "text-green-500" : "text-red-500"}`}>
                  {booking.status}
                </span>
                {booking.status !== "COMPLETED" && (
                  <button
                    onClick={() => markBookingAsCompleted(booking.id)}
                    className="ml-4 bg-green-500 text-white px-3 py-1 rounded"
                  >
                    Mark as Completed
                  </button>
                )}
              </li>
            ))}
          </ul>
        )}
      </div>

      {/* 👤 Manage Users */}
      <div className="mt-6 bg-white p-4 shadow rounded">
        <h3 className="text-lg font-bold">👤 Manage Users</h3>
        <button
          onClick={() => navigate("/admin/users")}
          className="mt-2 bg-blue-500 text-white px-4 py-2 rounded"
        >
          Manage Users
        </button>
      </div>

      {/* 📅 Manage Bookings */}
      <div className="mt-6 bg-white p-4 shadow rounded">
        <h3 className="text-lg font-bold">📅 Manage Bookings</h3>
        <button
          onClick={() => navigate("/admin/bookings")}
          className="mt-2 bg-red-500 text-white px-4 py-2 rounded"
        >
          Manage Bookings
        </button>
      </div>

      {/* 🚖 Manage Drivers */}
      <div className="mt-6 bg-white p-4 shadow rounded">
        <h3 className="text-lg font-bold">🚖 Manage Drivers</h3>
        <button
          onClick={() => navigate("/admin/drivers")}
          className="mt-2 bg-blue-500 text-white px-4 py-2 rounded"
        >
          Manage Drivers
        </button>
      </div>

      {/* 🚗 Manage Cars */}
      <div className="mt-6 bg-white p-4 shadow rounded">
        <h3 className="text-lg font-bold">🚗 Manage Cars</h3>
        <p>View and update vehicle information.</p>
        <button
          onClick={() => navigate("/admin/cars")}
          className="mt-2 bg-yellow-500 text-white px-4 py-2 rounded"
        >
          Manage Cars
        </button>
      </div>

      {/* 📂 Export Reports */}
      <button
        onClick={exportPDF}
        className="mt-4 bg-blue-500 text-white px-4 py-2 rounded"
      >
        Download Booking Reports (PDF)
      </button>

      <button
        onClick={() => navigate("/admin/reports")}
        className="mt-4 ml-2 bg-green-500 text-white px-4 py-2 rounded"
      >
        View Reports
      </button>
    </div>
  );
};

export default AdminDashboard;
