import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';

/* Auth */
import Login from './components/auth/AgentLogin'; // or Login
import Register from './components/auth/Register';
import AgentRegister from './components/auth/AgentRegister';
import AgentLogin from './components/auth/AgentLogin';

/* Optional: Navbar placeholder */
import Navbar from './components/common/Navbar';

import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="App">
          <Navbar /> {/* This can be simple placeholder for now */}

          <Routes>
            {/* Default route redirects to login */}
            <Route path="/" element={<Navigate to="/login" />} />

            {/* Auth routes */}
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/agent-register" element={<AgentRegister />} />
            <Route path="/agent-login" element={<AgentLogin />} />

            {/* Catch-all route for unknown paths */}
            <Route path="*" element={
              <div style={{ padding: '2rem', textAlign: 'center' }}>
                <h2>Page not found</h2>
                <p>Go to <a href="/login">Login</a> or <a href="/register">Register</a></p>
              </div>
            } />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
