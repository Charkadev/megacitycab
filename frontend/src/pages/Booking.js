import { useState } from "react";

export default function Booking() {
  const [pickup, setPickup] = useState("");
  const [dropoff, setDropoff] = useState("");
  const [fare, setFare] = useState(null);

  const handleBooking = async (e) => {
    e.preventDefault();

    const response = await fetch("http://localhost:8080/bookings/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      body: JSON.stringify({ pickupLocation: pickup, dropoffLocation: dropoff }),
    });

    const data = await response.json();
    if (response.ok) {
      setFare(data.fare);
    } else {
      alert("Booking failed!");
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold">Book a Ride</h1>
      <form onSubmit={handleBooking} className="mt-4">
        <input
          type="text"
          placeholder="Pickup Location"
          className="w-full p-2 mb-4 border rounded-lg"
          value={pickup}
          onChange={(e) => setPickup(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="Dropoff Location"
          className="w-full p-2 mb-4 border rounded-lg"
          value={dropoff}
          onChange={(e) => setDropoff(e.target.value)}
          required
        />
        <button className="w-full bg-blue-500 text-white p-2 rounded-lg">Book Now</button>
      </form>
      {fare && <p className="mt-4">Estimated Fare: ${fare}</p>}
    </div>
  );
}
