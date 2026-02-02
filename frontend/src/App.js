import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';

/* Auth */
import Login from './components/auth/AgentLogin';
import Register from './components/auth/Register';
import AgentRegister from './components/auth/AgentRegister';
import AgentLogin from './components/auth/AgentLogin';

/* Common */
import Navbar from './components/common/Navbar';

/* Dashboard */
import AgentDashboard from './components/dashboard/AgentDashboard';

/* Property */
import PropertyList from './components/property/PropertyList';
import AddProperty from './components/property/AddProperty';
import EditProperty from './components/property/EditProperty';
import PropertyDetail from './components/property/PropertyDetail';

import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />

        <Routes>
          {/* Default */}
          <Route path="/" element={<Navigate to="/login" />} />

          {/* Auth */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/agent-register" element={<AgentRegister />} />
          <Route path="/agent-login" element={<AgentLogin />} />

          {/* Dashboard */}
          <Route path="/dashboard" element={<AgentDashboard />} />

          {/* Property */}
          <Route path="/properties" element={<PropertyList />} />
          <Route path="/properties/add" element={<AddProperty />} />
          <Route path="/properties/edit/:id" element={<EditProperty />} />
          <Route path="/properties/:id" element={<PropertyDetail />} />

          {/* 404 */}
          <Route
            path="*"
            element={
              <div style={{ padding: '2rem', textAlign: 'center' }}>
                <h2>Page not found</h2>
                <p>Go to <a href="/login">Login</a></p>
              </div>
            }
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
