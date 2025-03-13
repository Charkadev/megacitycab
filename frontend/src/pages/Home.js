import React from "react";
import { Link } from "react-router-dom";

function Home() {
  return (
    <div className="text-center p-10">
      <h1 className="text-4xl font-bold text-blue-600">Welcome to MegaCityCab</h1>
      <p className="mt-4 text-lg">Book your ride with ease!</p>
      <div className="mt-6">
        <Link to="/login" className="px-6 py-3 bg-blue-500 text-white rounded-lg">Login</Link>
      </div>
    </div>
  );
}

export default Home;
