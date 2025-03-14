import { useEffect, useState } from "react";
import { api } from "../services/api";

const AdminCars = () => {
  const [cars, setCars] = useState([]);
  const [newCar, setNewCar] = useState({
    model: "",
    plateNumber: "",
    type: "",
    status: "Available", // ✅ Ensure Title Case for backend compatibility
  });

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

      console.log("📡 Fetching cars...");
      const response = await api.get("/admin/cars", {
        headers: { Authorization: `Bearer ${token}` },
      });

      console.log("✅ Cars fetched:", response.data);

      if (!Array.isArray(response.data)) {
        console.warn("⚠ No cars found.");
        setCars([]); // If response is not an array, set empty
        return;
      }

      // ✅ Fix: Use `_id` instead of `id`
      const formattedCars = response.data.map((car) => ({
        id: car._id,
        model: car.model,
        plateNumber: car.plateNumber,
        type: car.type,
        status: car.status.charAt(0).toUpperCase() + car.status.slice(1).toLowerCase(),
      }));

      setCars(formattedCars);
    } catch (error) {
      console.error("🚨 Error fetching cars:", error.response);
    }
  };

  const handleAddCar = async () => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            console.error("🚨 No auth token found");
            return;
        }

        // ✅ Convert status to Capitalized String before sending
        const carData = {
            ...newCar,
            status: newCar.status.charAt(0).toUpperCase() + newCar.status.slice(1).toLowerCase(),
        };

        await api.post("/admin/cars", carData, {
            headers: { Authorization: `Bearer ${token}` },
        });

        fetchCars();
        setNewCar({ model: "", plateNumber: "", type: "", status: "Available" });
    } catch (error) {
        console.error("🚨 Error adding car:", error);
    }
};



  const handleDeleteCar = async (id) => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found");
        return;
      }
      await api.delete(`/admin/cars/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchCars();
    } catch (error) {
      console.error("🚨 Error deleting car:", error);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mt-4 mb-2">🚗 Manage Cars</h1>

      <div className="mt-4">
        <h3 className="text-lg font-semibold">Add New Car</h3>
        <div className="flex gap-2 mt-2">
          <input
            type="text"
            placeholder="Model"
            className="p-2 border rounded w-1/4"
            value={newCar.model}
            onChange={(e) => setNewCar({ ...newCar, model: e.target.value })}
          />
          <input
            type="text"
            placeholder="Plate Number"
            className="p-2 border rounded w-1/4"
            value={newCar.plateNumber}
            onChange={(e) => setNewCar({ ...newCar, plateNumber: e.target.value })}
          />
          <input
            type="text"
            placeholder="Type (Sedan, SUV, etc.)"
            className="p-2 border rounded w-1/4"
            value={newCar.type}
            onChange={(e) => setNewCar({ ...newCar, type: e.target.value })}
          />
          <button
            onClick={handleAddCar}
            className="bg-green-500 text-white px-4 py-2 rounded"
          >
            ➕ Add Car
          </button>
        </div>
      </div>

      <h3 className="mt-6 text-lg font-semibold">🚖 Car List</h3>
      {cars.length === 0 ? (
        <p className="text-gray-600">No cars available.</p>
      ) : (
        <ul>
          {cars.map((car) => (
            <li key={car.id} className="p-2 border rounded mt-2 flex justify-between items-center">
              <span>{car.model} - {car.plateNumber} ({car.type}) - <strong>{car.status}</strong></span>
              <button onClick={() => handleDeleteCar(car.id)} className="bg-red-500 text-white px-3 py-1 rounded">🗑 Delete</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default AdminCars;
