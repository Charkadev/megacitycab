import { createContext, useContext, useState, useEffect } from "react";
import { api, setAuthToken } from "../services/api";
import { useNavigate } from "react-router-dom";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setAuthToken(token);
      fetchUser();
    }
  }, []);

  const fetchUser = async () => {
    try {
      const response = await api.get("/auth/user-info");
      setUser(response.data);
    } catch (error) {
      console.error("User not authenticated", error);
      logout();
    }
  };

  const login = async ({ email, password }) => {
    try {
      const response = await api.post("/auth/login", { email, password });
      const token = response.data.token;
      localStorage.setItem("token", token);
      setAuthToken(token);
      await fetchUser();
      navigate("/dashboard");
    } catch (error) {
      console.error("Login failed", error);
      alert("Invalid login credentials! Please try again.");
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    setAuthToken(null);
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
