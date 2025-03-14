import { createContext, useContext, useState, useEffect } from "react";
import { api } from "../services/api";
import { useNavigate } from "react-router-dom";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    console.log("🔍 Checking stored role:", role); // Debugging log

    if (token && role) {
      setUser({ role });
    }
  }, []);

  const login = async ({ email, password }) => {
    try {
      const response = await api.post("/auth/login", { email, password });
      const { token, role } = response.data;

      if (!token || !role) {
        throw new Error("❌ Token or Role missing in response");
      }

      console.log("📡 Received Role from Backend:", role);

      // ✅ Store token and role
      localStorage.setItem("token", token);
      localStorage.setItem("role", role);

      setUser({ role });

      // ✅ Redirect based on role
      if (role === "ROLE_ADMIN") {
        console.log("✅ Admin Login Successful! Redirecting to Admin Dashboard...");
        navigate("/admin/dashboard");
      } else {
        console.log("✅ User Login Successful! Redirecting to User Dashboard...");
        navigate("/dashboard");
      }
    } catch (error) {
      console.error("🚨 Login failed:", error.response?.data || error);
      alert("❌ Invalid credentials! Please try again.");
    }
  };

  const logout = () => {
    console.warn("🚨 Logging out user...");
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    setUser(null);
    navigate("/");
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
