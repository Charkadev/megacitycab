import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { api } from "../services/api";

const AdminDashboard = () => {
  const navigate = useNavigate();
  const [totalEarnings, setTotalEarnings] = useState(0);
  const [bookingSummary, setBookingSummary] = useState({ completed: 0, pending: 0, cancelled: 0 });

  useEffect(() => {
    const fetchAdminReports = async () => {
      try {
        const earningsRes = await api.get("/admin/reports/total-earnings");
        setTotalEarnings(earningsRes.data);

        const summaryRes = await api.get("/admin/reports/booking-summary");
        setBookingSummary(summaryRes.data);
      } catch (error) {
        console.error("Error fetching reports", error);
      }
    };

    fetchAdminReports();
  }, []);

  return (
    <div className="mt-4">
      <h2 className="text-xl font-semibold">Admin Panel</h2>
      <p>Manage bookings, drivers, and reports.</p>

      <div className="mt-4">
        <h3 className="text-lg font-bold">📊 System Reports</h3>
        <p>Total Earnings: ${totalEarnings}</p>
        <p>Bookings Summary:</p>
        <ul>
          <li>✅ Completed: {bookingSummary.completed}</li>
          <li>🟡 Pending: {bookingSummary.pending}</li>
          <li>❌ Cancelled: {bookingSummary.cancelled}</li>
        </ul>
      </div>

      <button
        onClick={() => navigate("/admin/reports")}
        className="mt-2 bg-green-500 text-white px-4 py-2 rounded"
      >
        View Reports
      </button>
    </div>
  );
};

export default AdminDashboard;
