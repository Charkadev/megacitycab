import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const BookRide = () => {
  const [pickup, setPickup] = useState("");
  const [dropoff, setDropoff] = useState("");
  const [fare, setFare] = useState("");
  const navigate = useNavigate();

  const handleBooking = async (e) => {
    e.preventDefault();
    try {
      await api.post("/bookings/create", {
        pickupLocation: pickup,
        dropoffLocation: dropoff,
        fare: parseFloat(fare),
      });
      alert("Booking Successful!");
      navigate("/dashboard");
    } catch (error) {
      alert("Booking Failed!");
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold">Book a Ride</h1>
      <form onSubmit={handleBooking} className="mt-4 space-y-4">
        <input
          type="text"
          placeholder="Pickup Location"
          className="w-full p-2 border border-gray-300 rounded"
          value={pickup}
          onChange={(e) => setPickup(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Dropoff Location"
          className="w-full p-2 border border-gray-300 rounded"
          value={dropoff}
          onChange={(e) => setDropoff(e.target.value)}
          required
        />
        <input
          type="number"
          placeholder="Fare ($)"
          className="w-full p-2 border border-gray-300 rounded"
          value={fare}
          onChange={(e) => setFare(e.target.value)}
          required
        />
        <button
          type="submit"
          className="w-full bg-blue-500 text-white py-2 rounded"
        >
          Book Now
        </button>
      </form>
    </div>
  );
};

export default BookRide;
