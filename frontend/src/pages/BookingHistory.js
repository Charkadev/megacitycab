// BookingHistory.js
import React, { useEffect, useState } from "react";
import { api } from "../services/api";
import { useAuth } from "../context/AuthContext";

function BookingHistory() {
  const [bookings, setBookings] = useState([]);
  const { user } = useAuth();

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await api.get(`/bookings/user/${user.id}`);
        setBookings(response.data);
      } catch (error) {
        console.error("Error fetching bookings", error);
      }
  };

  if (user && user.id) {
    fetchBookings();
  }
  }, [user]);

  return (
    <div className="p-8">
      <h2 className="text-2xl font-bold mb-4">Booking History</h2>
      {bookings.length > 0 ? (
        <table className="min-w-full bg-white shadow-md rounded-lg overflow-hidden">
          <thead className="bg-gray-200">
            <tr>
              <th className="py-3 px-4 text-left">Pickup Location</th>
              <th className="py-3 px-4 text-left">Dropoff Location</th>
              <th className="py-3 px-4 text-left">Fare</th>
              <th className="py-3 px-4 text-left">Status</th>
              <th className="py-3 px-4 text-left">Date & Time</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map((booking) => (
              <tr key={booking.id} className="border-t">
                <td className="py-2 px-4">{booking.pickupLocation}</td>
                <td className="py-2 px-4">{booking.dropoffLocation}</td>
                <td className="py-2 px-4">${booking.fare}</td>
                <td className="py-2 px-4">{booking.status}</td>
                <td className="py-2 px-4">
                  {new Date(booking.timestamp).toLocaleString()}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No bookings found.</p>
      )}
    </div>
  );
}

export default BookingHistory;
