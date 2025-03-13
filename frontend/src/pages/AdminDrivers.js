import { useEffect, useState } from "react";
import { api } from "../services/api";

const AdminDrivers = () => {
  const [drivers, setDrivers] = useState([]);
  const [newDriver, setNewDriver] = useState({ name: "", licenseNumber: "", phone: "" });

  useEffect(() => {
    const fetchDrivers = async () => {
      try {
        const response = await api.get("/drivers/all");
        setDrivers(response.data);
      } catch (error) {
        console.error("Error fetching drivers", error);
      }
    };

    fetchDrivers();
  }, []);

  const handleAddDriver = async (e) => {
    e.preventDefault();
    try {
      await api.post("/drivers/add", newDriver);
      alert("Driver added!");
      setNewDriver({ name: "", licenseNumber: "", phone: "" });
      window.location.reload();
    } catch (error) {
      alert("Failed to add driver");
    }
  };

  const handleDeleteDriver = async (id) => {
    try {
      await api.delete(`/drivers/delete/${id}`);
      alert("Driver deleted!");
      setDrivers(drivers.filter(driver => driver.id !== id));
    } catch (error) {
      alert("Failed to delete driver");
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold">Manage Drivers</h1>

      <form onSubmit={handleAddDriver} className="mt-4 space-y-4">
        <input
          type="text"
          placeholder="Driver Name"
          className="w-full p-2 border border-gray-300 rounded"
          value={newDriver.name}
          onChange={(e) => setNewDriver({ ...newDriver, name: e.target.value })}
          required
        />
        <input
          type="text"
          placeholder="License Number"
          className="w-full p-2 border border-gray-300 rounded"
          value={newDriver.licenseNumber}
          onChange={(e) => setNewDriver({ ...newDriver, licenseNumber: e.target.value })}
          required
        />
        <input
          type="text"
          placeholder="Phone Number"
          className="w-full p-2 border border-gray-300 rounded"
          value={newDriver.phone}
          onChange={(e) => setNewDriver({ ...newDriver, phone: e.target.value })}
          required
        />
        <button
          type="submit"
          className="w-full bg-blue-500 text-white py-2 rounded"
        >
          Add Driver
        </button>
      </form>

      <ul className="mt-6 space-y-4">
        {drivers.map((driver) => (
          <li key={driver.id} className="p-4 border rounded shadow flex justify-between">
            <div>
              <p><strong>Name:</strong> {driver.name}</p>
              <p><strong>License:</strong> {driver.licenseNumber}</p>
              <p><strong>Phone:</strong> {driver.phone}</p>
            </div>
            <button
              onClick={() => handleDeleteDriver(driver.id)}
              className="bg-red-500 text-white px-3 py-1 rounded"
            >
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AdminDrivers;
