import { useEffect, useState } from "react";
import { api } from "../services/api";

const User = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("🚨 No auth token found");
        return;
      }

      const response = await api.get("/admin/all-users", {
        headers: { Authorization: `Bearer ${token}` },
      });

      setUsers(response.data || []);
    } catch (error) {
      console.error("🚨 Error fetching users:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6">
      <h2 className="text-2xl font-semibold">👤 Manage Users</h2>
      {loading ? <p>Loading users...</p> : users.length === 0 ? <p>No users found.</p> : (
        <ul className="mt-4">
          {users.map((user) => (
            <li key={user.id} className="p-2 border rounded mb-2">
              {user.name} ({user.email}) - Role: <strong>{user.role}</strong>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default User;
