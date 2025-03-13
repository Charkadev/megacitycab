import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const UserDashboard = () => {
  const navigate = useNavigate();
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    const fetchUserBookings = async () => {
      try {
        const response = await api.get("/bookings/user");
        setBookings(response.data);
      } catch (error) {
        console.error("Error fetching bookings", error);
      }
    };

    fetchUserBookings();
  }, []);

  return (
    <div className="mt-4">
      <h2 className="text-xl font-semibold">Your Bookings</h2>
      {bookings.length === 0 ? (
        <p>No bookings yet.</p>
      ) : (
        <ul className="mt-2">
          {bookings.map((booking) => (
            <li key={booking.id} className="p-2 border rounded mb-2">
              🚖 {booking.pickupLocation} → {booking.dropoffLocation}  
              <span className={`ml-2 ${booking.status === "COMPLETED" ? "text-green-500" : "text-yellow-500"}`}>
                {booking.status}
              </span>
            </li>
          ))}
        </ul>
      )}
      <button
        onClick={() => navigate("/book-ride")}
        className="mt-2 bg-blue-500 text-white px-4 py-2 rounded"
      >
        Book a Ride
      </button>
    </div>
  );
};

export default UserDashboard;
