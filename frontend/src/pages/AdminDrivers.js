import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const AdminDrivers = () => {
  const navigate = useNavigate();
  const [drivers, setDrivers] = useState([]);
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCar, setSelectedCar] = useState({});

  useEffect(() => {
    fetchDrivers();
    fetchAvailableCars();
  }, []);

  const fetchDrivers = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found");
        return;
      }
      const response = await api.get("/admin/drivers/all", { headers: { Authorization: `Bearer ${token}` } });
      setDrivers(response.data || []);
    } catch (error) {
      console.error("🚨 Error fetching drivers:", error);
    } finally {
      setLoading(false);
    }
  };

  const fetchAvailableCars = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found");
        return;
      }
      const response = await api.get("/admin/cars/available", { headers: { Authorization: `Bearer ${token}` } });
      setCars(response.data || []);
    } catch (error) {
      console.error("🚨 Error fetching available cars:", error);
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold">🚖 Manage Drivers</h2>
      <p className="text-gray-600">View, assign cars, and remove drivers.</p>

      {loading ? (
        <p>Loading drivers...</p>
      ) : drivers.length === 0 ? (
        <p>No drivers found.</p>
      ) : (
        <ul className="mt-4">
          {drivers.map((driver) => (
            <li key={driver.id} className="p-3 border rounded mb-2">
              {driver.name} - {driver.phone} 
            </li>
          ))}
        </ul>
      )}

      <button onClick={() => navigate("/admin/add-driver")} className="mt-4 bg-green-500 text-white px-4 py-2 rounded">
        ➕ Add New Driver
      </button>
    </div>
  );
};

export default AdminDrivers;
