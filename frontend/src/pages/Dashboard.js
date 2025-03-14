import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";
import { api } from "../services/api";
import { useNavigate } from "react-router-dom";
import UserDashboard from "../components/UserDashboard";
import AdminDashboard from "../components/AdminDashboard";

const Dashboard = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [role, setRole] = useState(null);
  const [loading, setLoading] = useState(true); // ✅ Add loading state

  useEffect(() => {
    const fetchUserRole = async () => {
      try {
        const response = await api.get("/auth/user-info");
        setRole(response.data.role);
      } catch (error) {
        console.error("Error fetching user role", error);
        logout(); // Logout if error occurs
      } finally {
        setLoading(false); // ✅ Ensure loading stops
      }
    };

    if (user) fetchUserRole();
  }, [user, logout]);

  if (!user) return null;
  if (loading) return <p className="text-center">Loading...</p>; // ✅ Prevents blank page

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold">Dashboard</h1>
      <p className="text-lg mt-2">Welcome, {role === "ROLE_ADMIN" ? "Admin" : "User"}!</p>

      {role === "ROLE_USER" && <UserDashboard />}
      {role === "ROLE_ADMIN" && <AdminDashboard />}

      <button onClick={logout} className="mt-6 bg-red-500 text-white px-4 py-2 rounded">
        Logout
      </button>
    </div>
  );
};

export default Dashboard;
