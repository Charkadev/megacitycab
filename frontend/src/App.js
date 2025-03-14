import { useLocation } from "react-router-dom";
import AppRoutes from "./routes";
import Navbar from "./components/Navbar"; // ✅ Import Navbar

function App() {
  const location = useLocation(); // ✅ Get the current route

  // ✅ Hide Navbar on Login & Register pages
  const hideNavbar = location.pathname === "/" || location.pathname === "/register";

  return (
    <div className="App">
      {!hideNavbar && <Navbar />} {/* ✅ Show Navbar except on login/register */}
      <AppRoutes />
    </div>
  );
}

export default App;
