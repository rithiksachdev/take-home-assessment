import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'; // Ensure you have the correct path to your CSS file

const Navbar = () => {
  return (
    <nav className="navbar">
      {/* Use Link component for HealthTrack to navigate back to home */}
      <Link to="/" className="navbar-brand">HealthTrack</Link>
      <div className="navbar-links">
        <Link className="nav-link" to="/create">Create Patient</Link>
        <Link className="nav-link" to="/update">Update Patient</Link>
        <Link className="nav-link" to="/delete">Delete Patient</Link>
        <Link className="nav-link" to="/get">Get Patient</Link>
      </div>
    </nav>
  );
};

export default Navbar;
