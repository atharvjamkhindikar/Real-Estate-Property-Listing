import React, { useState, useEffect, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { propertyService, propertyImageService } from '../../services/api';
import { useAuth } from '../../context/AuthContext';
import './PropertyDetail.css';

const PropertyDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { user } = useAuth();

    const [property, setProperty] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [images, setImages] = useState([]);
    const [currentImageIndex, setCurrentImageIndex] = useState(0);

    // 🔹 Fetch property images (memoized)
    const fetchPropertyImages = useCallback(async (propertyId) => {
        try {
            const response = await propertyImageService.getPropertyImages(propertyId);
            const imageData = response.data?.data || response.data;
            setImages(Array.isArray(imageData) ? imageData : []);
            setCurrentImageIndex(0);
        } catch (err) {
            console.error('Error fetching property images:', err);
            setImages([]);
        }
    }, []);

    // 🔹 Fetch property details (memoized)
    const fetchProperty = useCallback(async () => {
        try {
            setLoading(true);
            setError(null);

            const response = await propertyService.getPropertyById(id);
            const apiResponse = response.data;
            setProperty(apiResponse?.data || apiResponse);

            await fetchPropertyImages(id);
        } catch (err) {
            console.error('Error fetching property:', err);
            setError('Failed to fetch property details.');
        } finally {
            setLoading(false);
        }
    }, [id, fetchPropertyImages]);

    // 🔹 Load property on mount / id change
    useEffect(() => {
        fetchProperty();
    }, [fetchProperty]);

    const canEditOrDelete = () => {
        if (!user || !property) return false;
        const isAdmin = user.userType === 'ADMIN' || user.role === 'ADMIN';
        const isAgent = user.userType === 'AGENT' || user.role === 'AGENT';
        const isOwner = property.owner?.id === user.id;
        return isAdmin || isAgent || isOwner;
    };

    const handleEdit = () => {
        navigate(`/edit-property/${id}`);
    };

    const handleDelete = async () => {
        if (!window.confirm('Are you sure you want to delete this property?')) return;

        try {
            await propertyService.deleteProperty(id);
            alert('Property deleted successfully!');
            navigate('/');
        } catch (err) {
            console.error('Error deleting property:', err);
            alert('Failed to delete property');
        }
    };

    if (loading) {
        return <div className="loading">Loading property details...</div>;
    }

    if (error || !property) {
        return (
            <div className="error-container">
                <div className="error">{error || 'Property not found'}</div>
                <button onClick={() => navigate('/')} className="back-btn">
                    Back to Listings
                </button>
            </div>
        );
    }

    return (
        <div className="property-detail-container">
            <button onClick={() => navigate('/')} className="back-btn">
                ← Back to Listings
            </button>

            <div className="property-detail">
                <div className="detail-image">
                    {images.length > 0 ? (
                        <img
                            src={images[currentImageIndex].imageUrl}
                            alt={property.title}
                        />
                    ) : (
                        <div className="no-image">📸 No Images Available</div>
                    )}
                </div>

                <div className="detail-content">
                    <h1>{property.title}</h1>
                    <p>{property.address}</p>

                    {canEditOrDelete() && (
                        <div className="edit-delete-buttons">
                            <button onClick={handleEdit}>✏️ Edit</button>
                            <button onClick={handleDelete}>🗑️ Delete</button>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default PropertyDetail;
