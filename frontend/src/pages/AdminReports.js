import { useEffect, useState } from "react";
import { api } from "../services/api";

const AdminReports = () => {
  const [totalEarnings, setTotalEarnings] = useState(0);
  const [bookingSummary, setBookingSummary] = useState({});

  useEffect(() => {
    const fetchReports = async () => {
      try {
        const earningsResponse = await api.get("/admin/reports/total-earnings");
        const summaryResponse = await api.get("/admin/reports/booking-summary");

        setTotalEarnings(earningsResponse.data);
        setBookingSummary(summaryResponse.data);
      } catch (error) {
        console.error("Error fetching reports", error);
      }
    };

    fetchReports();
  }, []);

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold">Admin Reports</h1>

      <div className="mt-4 bg-white shadow p-4 rounded">
        <h2 className="text-xl font-semibold">Total Earnings</h2>
        <p className="text-2xl font-bold text-green-500">${totalEarnings}</p>
      </div>

      <div className="mt-4 bg-white shadow p-4 rounded">
        <h2 className="text-xl font-semibold">Booking Summary</h2>
        <p>Total Bookings: {bookingSummary["Total Bookings"]}</p>
        <p>Completed: {bookingSummary["Completed Bookings"]}</p>
        <p>Pending: {bookingSummary["Pending Bookings"]}</p>
        <p>Cancelled: {bookingSummary["Cancelled Bookings"]}</p>
      </div>
    </div>
  );
};

export default AdminReports;
