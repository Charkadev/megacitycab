import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <nav className="bg-blue-600 text-white p-4 flex justify-between">
      <h1 className="text-xl font-bold">MegaCityCab</h1>

      <div className="space-x-4">
        {user && (
          <>
            <Link to="/dashboard" className="hover:underline">Dashboard</Link>
            <Link to="/book-ride" className="hover:underline">Book a Ride</Link>
            {user.role === "ROLE_ADMIN" && (
              <>
                <Link to="/admin/reports" className="hover:underline">Reports</Link>
                <Link to="/admin/drivers" className="hover:underline">Manage Drivers</Link>
              </>
            )}
            <Link to="/help" className="hover:underline">Help</Link> {/* ✅ Added Help */}
            <button onClick={logout} className="ml-4 bg-red-500 px-3 py-1 rounded">
              Logout
            </button>
          </>
        )}
        {!user && (
          <>
            <Link to="/" className="hover:underline">Login</Link>
            <Link to="/register" className="hover:underline">Register</Link>
            <Link to="/help" className="hover:underline">Help</Link> {/* ✅ Added Help */}
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
