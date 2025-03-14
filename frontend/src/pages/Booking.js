import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const Booking = () => {
  const navigate = useNavigate();
  const [booking, setBooking] = useState({
    pickupLocation: "",
    dropoffLocation: "",
    selectedCarId: "", // ✅ Store selected car ID
  });
  const [availableCars, setAvailableCars] = useState([]);

  useEffect(() => {
    fetchAvailableCars();
  }, []);

  // ✅ Fetch only available cars from the backend
  const fetchAvailableCars = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found");
        return;
      }

      const response = await api.get("/admin/cars/available", {
        headers: { Authorization: `Bearer ${token}` },
      });

      setAvailableCars(response.data || []);
    } catch (error) {
      console.error("🚨 Error fetching available cars:", error);
    }
  };

  // ✅ Handle booking submission
  const handleBooking = async () => {
    if (!booking.pickupLocation || !booking.dropoffLocation || !booking.selectedCarId) {
      alert("❌ Please fill all fields including selecting a car.");
      return;
    }

    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found");
        return;
      }

      const response = await api.post("/bookings/create", booking, {
        headers: { Authorization: `Bearer ${token}` },
      });

      alert("✅ Booking successful!");
      navigate("/booking-history");
    } catch (error) {
      console.error("🚨 Error creating booking:", error);
      alert("❌ Booking failed.");
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold">🚖 Book a Ride</h2>
      <p className="text-gray-600">Select your locations and car to proceed.</p>

      <div className="mt-4 space-y-2">
        <input
          type="text"
          placeholder="Pickup Location"
          className="p-2 border rounded w-full"
          value={booking.pickupLocation}
          onChange={(e) => setBooking({ ...booking, pickupLocation: e.target.value })}
        />
        <input
          type="text"
          placeholder="Drop-off Location"
          className="p-2 border rounded w-full"
          value={booking.dropoffLocation}
          onChange={(e) => setBooking({ ...booking, dropoffLocation: e.target.value })}
        />

        {/* ✅ Car Selection Dropdown */}
        <select
          className="p-2 border rounded w-full"
          value={booking.selectedCarId}
          onChange={(e) => setBooking({ ...booking, selectedCarId: e.target.value })}
        >
          <option value="">Select a Car</option>
          {availableCars.map((car) => (
            <option key={car.id} value={car.id}>
              {car.model} - {car.plateNumber}
            </option>
          ))}
        </select>
      </div>

      <button
        onClick={handleBooking}
        className="mt-4 bg-green-500 text-white px-4 py-2 rounded"
      >
        Book Now
      </button>
    </div>
  );
};

export default Booking;
