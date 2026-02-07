import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';

/* Common */
import Navbar from './components/common/Navbar';

/* Auth */
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import AgentLogin from './components/auth/AgentLogin';
import AgentRegister from './components/auth/AgentRegister';

/* Dashboards */
import AgentDashboard from './components/dashboard/AgentDashboard';
import AdminDashboard from './components/dashboard/AdminDashboard';

/* Property */
import PropertyList from './components/property/PropertyList';
import PropertyDetail from './components/property/PropertyDetail';
import AddProperty from './components/property/AddProperty';
import EditProperty from './components/property/EditProperty';
import PropertyImages from './components/property/PropertyImages';
import Favorites from './components/property/Favorites';
import ScheduleViewing from './components/property/ScheduleViewing';

/* Search */
import AdvancedSearch from './components/search/AdvancedSearch';
import BuilderGroupFilter from './components/search/BuilderGroupFilter';
import SearchHistory from './components/search/SearchHistory';

/* Admin / User */
import UserManagement from './components/user/UserManagement';

/* Subscription */
import SubscriptionManagement from './components/subscription/SubscriptionManagement';
import UpgradePlan from './components/subscription/UpgradePlan';

/* Error */
import Unauthorized from './components/error/Unauthorized';

/* Routing Guards */
import {
  ProtectedRoute,
  AdminRoute,
  SubscriptionRoute
} from './components/routing/ProtectedRoute';

import './App.css';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />

        <Routes>
          {/* Default */}
          <Route path="/" element={<Navigate to="/properties" />} />

          {/* Public Routes */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/agent-login" element={<AgentLogin />} />
          <Route path="/agent-register" element={<AgentRegister />} />

          <Route path="/properties" element={<PropertyList />} />
          <Route path="/properties/:id" element={<PropertyDetail />} />
          <Route path="/builders" element={<BuilderGroupFilter />} />

          <Route path="/unauthorized" element={<Unauthorized />} />
          <Route path="/upgrade" element={<UpgradePlan />} />

          {/* Agent Dashboard */}
          <Route
            path="/dashboard"
            element={
              <ProtectedRoute>
                <AgentDashboard />
              </ProtectedRoute>
            }
          />

          {/* Protected Property Routes */}
          <Route
            path="/properties/add"
            element={
              <ProtectedRoute>
                <AddProperty />
              </ProtectedRoute>
            }
          />

          <Route
            path="/properties/edit/:id"
            element={
              <ProtectedRoute>
                <EditProperty />
              </ProtectedRoute>
            }
          />

          <Route
            path="/properties/images/:id"
            element={
              <ProtectedRoute>
                <PropertyImages />
              </ProtectedRoute>
            }
          />

          <Route
            path="/schedule-viewing/:id"
            element={
              <ProtectedRoute>
                <ScheduleViewing />
              </ProtectedRoute>
            }
          />

          {/* Subscription Routes (BASIC+) */}
          <Route
            path="/search"
            element={
              <SubscriptionRoute requiredSubscription="BASIC">
                <AdvancedSearch />
              </SubscriptionRoute>
            }
          />

          <Route
            path="/favorites"
            element={
              <SubscriptionRoute requiredSubscription="BASIC">
                <Favorites />
              </SubscriptionRoute>
            }
          />

          <Route
            path="/history"
            element={
              <SubscriptionRoute requiredSubscription="BASIC">
                <SearchHistory />
              </SubscriptionRoute>
            }
          />

          {/* Admin Routes */}
          <Route
            path="/admin-dashboard"
            element={
              <AdminRoute>
                <AdminDashboard />
              </AdminRoute>
            }
          />

          <Route
            path="/users"
            element={
              <AdminRoute>
                <UserManagement />
              </AdminRoute>
            }
          />

          <Route
            path="/subscriptions"
            element={
              <AdminRoute>
                <SubscriptionManagement />
              </AdminRoute>
            }
          />

          {/* 404 */}
          <Route
            path="*"
            element={
              <div style={{ padding: '2rem', textAlign: 'center' }}>
                <h2>Page Not Found</h2>
                <p>
                  Go to <a href="/login">Login</a>
                </p>
              </div>
            }
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
