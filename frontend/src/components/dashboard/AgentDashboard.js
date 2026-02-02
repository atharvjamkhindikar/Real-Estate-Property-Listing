import React, { useState, useEffect, useCallback } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { propertyService, propertyImageService } from '../../services/api';
import PropertyCard from '../property/PropertyCard';
import './AgentDashboard.css';

const AgentDashboard = () => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const [properties, setProperties] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const [stats, setStats] = useState({
        totalProperties: 0,
        activeListings: 0,
        rentListings: 0,
    });

    const [showAddImageModal, setShowAddImageModal] = useState(false);
    const [selectedProperty, setSelectedProperty] = useState(null);
    const [selectedImages, setSelectedImages] = useState([]);
    const [imagePreviews, setImagePreviews] = useState([]);
    const [uploadingImages, setUploadingImages] = useState(false);

    //  FIX: Wrapped with useCallback
    const fetchAgentProperties = useCallback(async () => {
        try {
            setLoading(true);
            setError(null);

            const response = await propertyService.getAvailableProperties(0, 100);
            let allProperties = response.data.data?.content || response.data.data || [];
            allProperties = Array.isArray(allProperties) ? allProperties : [];

            const agentProperties = allProperties.filter(
                p => p.owner && p.owner.id === user.id
            );

            setProperties(agentProperties);

            setStats({
                totalProperties: agentProperties.length,
                activeListings: agentProperties.filter(p => p.listingType === 'FOR_SALE').length,
                rentListings: agentProperties.filter(p => p.listingType === 'FOR_RENT').length,
            });
        } catch (err) {
            console.error('Error:', err);
            setError('Failed to load properties');
            setProperties([]);
        } finally {
            setLoading(false);
        }
    }, [user]);

    //  FIX: Proper dependency array
    useEffect(() => {
        if (!user) {
            navigate('/agent-login');
            return;
        }

        if (user.userType !== 'AGENT' && user.role !== 'AGENT') {
            navigate('/unauthorized');
            return;
        }

        fetchAgentProperties();
    }, [user, navigate, fetchAgentProperties]);

    const handleAddImages = (property) => {
        setSelectedProperty(property);
        setShowAddImageModal(true);
    };

    const handleImageSelect = (e) => {
        const files = Array.from(e.target.files);
        if (!files.length) return;

        const validFiles = files.filter(file => {
            if (!file.type.startsWith('image/')) return false;
            if (file.size > 5 * 1024 * 1024) return false;
            return true;
        });

        setSelectedImages(prev => [...prev, ...validFiles]);

        validFiles.forEach(file => {
            const reader = new FileReader();
            reader.onload = (event) => {
                setImagePreviews(prev => [...prev, event.target.result]);
            };
            reader.readAsDataURL(file);
        });
    };

    const removeImage = (index) => {
        setSelectedImages(prev => prev.filter((_, i) => i !== index));
        setImagePreviews(prev => prev.filter((_, i) => i !== index));
    };

    const handleUploadImages = async (e) => {
        e.preventDefault();
        if (!selectedImages.length) return;

        setUploadingImages(true);
        try {
            for (let i = 0; i < selectedImages.length; i++) {
                const formData = new FormData();
                formData.append('file', selectedImages[i]);
                formData.append('caption', '');
                formData.append('isPrimary', String(i === 0));
                formData.append('displayOrder', String(i));

                await propertyImageService.addImage(selectedProperty.id, formData);
            }

            setSuccess('Images uploaded successfully');
            setSelectedImages([]);
            setImagePreviews([]);
            setShowAddImageModal(false);

            fetchAgentProperties();
            setTimeout(() => setSuccess(null), 3000);
        } catch (err) {
            console.error(err);
            alert('Failed to upload images');
        } finally {
            setUploadingImages(false);
        }
    };

    const handleEditProperty = (propertyId) => {
        navigate(`/edit-property/${propertyId}`);
    };

    const handleDeleteProperty = async (propertyId) => {
        if (!window.confirm('Delete this property?')) return;

        try {
            await propertyService.deleteProperty(propertyId);
            setSuccess('Property deleted successfully');
            fetchAgentProperties();
            setTimeout(() => setSuccess(null), 3000);
        } catch (err) {
            console.error(err);
            alert('Failed to delete property');
        }
    };

    const handleLogout = () => {
        logout();
        navigate('/agent-login');
    };

    if (loading) {
        return <div className="agent-dashboard-loading">Loading...</div>;
    }

    return (
        <div className="agent-dashboard-container">
            <h1>🏢 Agent Dashboard</h1>

            <button onClick={handleLogout}>Logout</button>

            {error && <p>{error}</p>}
            {success && <p>{success}</p>}

            <div className="properties-grid">
                {properties.map(property => (
                    <PropertyCard
                        key={property.id}
                        property={property}
                        userId={user.id}
                        showFavoriteButton={false}
                    />
                ))}
            </div>
        </div>
    );
};

export default AgentDashboard;
