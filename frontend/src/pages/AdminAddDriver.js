import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const AdminAddDriver = () => {
  const navigate = useNavigate();
  const [driver, setDriver] = useState({ name: "", phone: "", assignedCarId: "" });
  const [cars, setCars] = useState([]);

  useEffect(() => {
    fetchCars();
  }, []);

  const fetchCars = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found");
        return;
      }

      console.log("📡 Fetching available cars...");
      const response = await api.get("/admin/cars/available", { 
        headers: { Authorization: `Bearer ${token}` } 
      });

      console.log("✅ Available Cars:", response.data);

      if (!Array.isArray(response.data)) {
        console.warn("⚠ Unexpected API response:", response.data);
        setCars([]);
        return;
      }

      // ✅ Fix: Use `_id` instead of `id`
      const formattedCars = response.data.map((car) => ({
        id: car._id,
        model: car.model,
        plateNumber: car.plateNumber,
      }));

      setCars(formattedCars);
    } catch (error) {
      console.error("🚨 Error fetching available cars:", error.response);
    }
  };

  const handleAddDriver = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found");
        alert("Authentication error! Please login again.");
        return;
      }

      const response = await api.post("/admin/drivers/add", driver, { 
        headers: { Authorization: `Bearer ${token}` } 
      });

      alert("✅ Driver added successfully!");
      navigate("/admin/drivers");
    } catch (error) {
      console.error("🚨 Error adding driver:", error);
      alert(`Failed to add driver: ${error.response?.data?.error || "Unknown error"}`);
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold">➕ Add New Driver</h2>

      <div className="mt-4 space-y-2">
        <input
          type="text"
          placeholder="Driver Name"
          className="p-2 border rounded w-full"
          value={driver.name}
          onChange={(e) => setDriver({ ...driver, name: e.target.value })}
        />
        <input
          type="text"
          placeholder="Phone Number"
          className="p-2 border rounded w-full"
          value={driver.phone}
          onChange={(e) => setDriver({ ...driver, phone: e.target.value })}
        />

        {/* ✅ Assign Car Dropdown */}
        <select
          className="p-2 border rounded w-full"
          value={driver.assignedCarId}
          onChange={(e) => setDriver({ ...driver, assignedCarId: e.target.value })}
        >
          <option value="">Select a Car (Optional)</option>
          {cars.length > 0 ? (
            cars.map((car) => (
              <option key={car.id} value={car.id}>
                {car.model} - {car.plateNumber}
              </option>
            ))
          ) : (
            <option disabled>No Available Cars</option>
          )}
        </select>
      </div>

      {/* ✅ Add Driver Button */}
      <button
        onClick={handleAddDriver}
        className="mt-4 bg-green-500 text-white px-4 py-2 rounded"
      >
        ➕ Add Driver
      </button>
    </div>
  );
};

export default AdminAddDriver;
