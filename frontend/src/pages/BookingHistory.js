import { useEffect, useState } from "react";
import { api } from "../services/api";
import { useAuth } from "../context/AuthContext";

const BookingHistory = () => {
  const { user } = useAuth();
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await api.get(`/bookings/user/${user.id}`);
        setBookings(response.data);
      } catch (error) {
        console.error("Error fetching bookings", error);
      }
    };

    fetchBookings();
  }, [user.id]);

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold">Booking History</h1>
      <ul className="mt-4 space-y-4">
        {bookings.map((booking) => (
          <li key={booking.id} className="p-4 border rounded shadow">
            <p><strong>Pickup:</strong> {booking.pickupLocation}</p>
            <p><strong>Dropoff:</strong> {booking.dropoffLocation}</p>
            <p><strong>Fare:</strong> ${booking.fare}</p>
            <p><strong>Status:</strong> <span className={`text-${booking.status === 'COMPLETED' ? 'green' : 'yellow'}-500`}>{booking.status}</span></p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BookingHistory;
