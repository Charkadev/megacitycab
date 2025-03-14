import { useEffect, useState } from "react";
import { api } from "../services/api";

const ManageBookings = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchBookings();
  }, []);

  const fetchBookings = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found. Redirecting to login...");
        window.location.href = "/";
        return;
      }

      console.log("📡 Fetching all bookings...");
      const response = await api.get("/admin/all-bookings", {
        headers: { Authorization: `Bearer ${token}` },
      });

      console.log("✅ Bookings fetched:", response.data);
      setBookings(response.data || []);
    } catch (error) {
      console.error("🚨 Error fetching bookings:", error.response);
      if (error.response?.status === 401) {
        alert("❌ Unauthorized! Please log in again.");
        localStorage.removeItem("token");
        window.location.href = "/";
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold">📅 Manage All Bookings</h2>
      {loading ? <p>Loading bookings...</p> : bookings.length === 0 ? <p>No bookings found.</p> : (
        <ul className="mt-4">
          {bookings.map((booking) => (
            <li key={booking.id} className="p-2 border rounded mb-2">
              {booking.pickupLocation} → {booking.dropoffLocation} |
              Status: <strong>{booking.status}</strong>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ManageBookings;
