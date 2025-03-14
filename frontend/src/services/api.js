import axios from "axios";

const API_URL = "http://localhost:8080";

const api = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// ✅ Automatically attach JWT token to every request
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      console.log("📡 Sending Request with Token:", token);
      config.headers.Authorization = `Bearer ${token}`;
    } else {
      console.warn("🚨 No token found in localStorage");
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// ✅ Handle token expiration errors
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response && error.response.status === 401) {
      console.warn("🚨 401 Unauthorized Error! Checking token validity...");

      const token = localStorage.getItem("token");

      if (!token) {
        console.warn("🚨 No token found. Logging out...");
        localStorage.removeItem("token");
        window.location.href = "/";
        return Promise.reject(error);
      }

      try {
        // ✅ Decode the token to check expiration
        const payload = JSON.parse(atob(token.split(".")[1]));
        const expiration = payload.exp * 1000;

        if (Date.now() >= expiration) {
          console.warn("⏳ Token has expired! Logging out...");
          localStorage.removeItem("token");
          window.location.href = "/";
        } else {
          console.warn("⏳ Token is valid but received 401. Retrying...");
        }
      } catch (decodeError) {
        console.error("🚨 Error decoding token:", decodeError);
        localStorage.removeItem("token");
        window.location.href = "/";
      }
    }

    return Promise.reject(error);
  }
);

export { api };
