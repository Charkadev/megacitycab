import { Routes, Route, Navigate } from "react-router-dom";
import { useAuth } from "./context/AuthContext";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import BookRide from "./pages/BookRide";
import AdminReports from "./pages/AdminReports";
import AdminDrivers from "./pages/AdminDrivers";
import BookingHistory from "./pages/BookingHistory";

const ProtectedRoute = ({ element }) => {
  const { user } = useAuth();
  return user ? element : <Navigate to="/" />;
};

function AppRoutes() {
  return (
    <Routes> {/* ✅ Removed <Router> wrapper */}
      <Route path="/" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/dashboard" element={<ProtectedRoute element={<Dashboard />} />} />
      <Route path="/book-ride" element={<ProtectedRoute element={<BookRide />} />} />
      <Route path="/admin/reports" element={<ProtectedRoute element={<AdminReports />} />} />
      <Route path="/booking-history" element={<ProtectedRoute element={<BookingHistory />} />} />
      <Route path="/admin/drivers" element={<ProtectedRoute element={<AdminDrivers />} />} />
    </Routes>
  );
}

export default AppRoutes;
