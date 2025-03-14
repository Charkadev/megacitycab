import { Routes, Route, Navigate } from "react-router-dom";
import { useAuth } from "./context/AuthContext";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import BookRide from "./pages/BookRide";
import Booking from "./pages/Booking";
import BookingHistory from "./pages/BookingHistory";
import AdminReports from "./pages/AdminReports";
import AdminDrivers from "./pages/AdminDrivers";
import AdminCars from "./pages/AdminCars";
import AdminAddDriver from "./pages/AdminAddDriver";
import ManageBookings from "./pages/ManageBookings"; // ✅ Admin Bookings Page
import User from "./pages/User"; // ✅ Admin Users Page
import Help from "./pages/Help";

const ProtectedRoute = ({ element }) => {
  const { user } = useAuth();
  const token = localStorage.getItem("token");

  if (!token) {
    console.warn("🚨 No valid token. Redirecting to login...");
    return <Navigate to="/" />;
  }

  return user ? element : <Navigate to="/" />;
};

function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/dashboard" element={<ProtectedRoute element={<Dashboard />} />} />
      <Route path="/book-ride" element={<ProtectedRoute element={<BookRide />} />} />
      <Route path="/booking" element={<ProtectedRoute element={<Booking />} />} />
      <Route path="/booking-history" element={<ProtectedRoute element={<BookingHistory />} />} />
      <Route path="/admin/reports" element={<ProtectedRoute element={<AdminReports />} />} />
      <Route path="/admin/drivers" element={<ProtectedRoute element={<AdminDrivers />} />} />
      <Route path="/admin/cars" element={<ProtectedRoute element={<AdminCars />} />} />
      <Route path="/admin/add-driver" element={<ProtectedRoute element={<AdminAddDriver />} />} />
      
      {/* ✅ New Routes for Admin Management */}
      <Route path="/admin/bookings" element={<ProtectedRoute element={<ManageBookings />} />} />
      <Route path="/admin/users" element={<ProtectedRoute element={<User />} />} />

      <Route path="/help" element={<Help />} />
    </Routes>
  );
}

export default AppRoutes;
