import React, { useState } from 'react';
import { AlertCircle, CheckCircle, Loader } from 'lucide-react';

export default function DhvaniApp() {
    const [formData, setFormData] = useState({
        username: '',
        phoneNumber: '',
        password: '',
        confirmPassword: '',
        fullName: '',
        dateOfBirth: '',
        country: 'INDIA',
        gender: 'MALE',
    });

    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMessage('');
        setError('');

        try {
            const response = await fetch('http://localhost:8080/dhvani/user/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            const data = await response.json();

            if (response.ok) {
                setMessage(data.message || 'Signup successful!');
                setFormData({
                    username: '',
                    phoneNumber: '',
                    password: '',
                    confirmPassword: '',
                    fullName: '',
                    dateOfBirth: '',
                    country: 'INDIA',
                    gender: 'MALE',
                });
            } else {
                setError(data.message || 'Signup failed. Please try again.');
            }
        } catch (err) {
            setError('Connection error. Make sure the backend is running on http://localhost:8080');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-purple-900 via-black to-purple-900 flex items-center justify-center p-4">
            <div className="w-full max-w-md">
                {/* Header */}
                <div className="text-center mb-8">
                    <h1 className="text-4xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-purple-400 to-pink-400 mb-2">
                        Dhvani
                    </h1>
                    <p className="text-gray-400">Your Music, Your Mood</p>
                </div>

                {/* Form Card */}
                <div className="bg-gray-900 rounded-lg shadow-2xl p-8 border border-purple-500/20">
                    <h2 className="text-2xl font-bold text-white mb-6">Create Account</h2>

                    {/* Messages */}
                    {message && (
                        <div className="mb-4 p-4 bg-green-900/30 border border-green-500 rounded-lg flex items-start gap-3">
                            <CheckCircle className="w-5 h-5 text-green-400 flex-shrink-0 mt-0.5" />
                            <p className="text-green-300 text-sm">{message}</p>
                        </div>
                    )}

                    {error && (
                        <div className="mb-4 p-4 bg-red-900/30 border border-red-500 rounded-lg flex items-start gap-3">
                            <AlertCircle className="w-5 h-5 text-red-400 flex-shrink-0 mt-0.5" />
                            <p className="text-red-300 text-sm">{error}</p>
                        </div>
                    )}

                    <form onSubmit={handleSubmit} className="space-y-4">
                        {/* Full Name */}
                        <div>
                            <label className="block text-gray-300 text-sm font-medium mb-1">
                                Full Name
                            </label>
                            <input
                                type="text"
                                name="fullName"
                                value={formData.fullName}
                                onChange={handleChange}
                                placeholder="John Doe"
                                className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg text-white placeholder-gray-500 focus:border-purple-500 focus:outline-none transition"
                                required
                            />
                        </div>

                        {/* Username */}
                        <div>
                            <label className="block text-gray-300 text-sm font-medium mb-1">
                                Username
                            </label>
                            <input
                                type="text"
                                name="username"
                                value={formData.username}
                                onChange={handleChange}
                                placeholder="john_doe"
                                className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg text-white placeholder-gray-500 focus:border-purple-500 focus:outline-none transition"
                                required
                            />
                            <p className="text-gray-500 text-xs mt-1">Lowercase letters, numbers, underscores only</p>
                        </div>

                        {/* Phone Number */}
                        <div>
                            <label className="block text-gray-300 text-sm font-medium mb-1">
                                Phone Number
                            </label>
                            <input
                                type="tel"
                                name="phoneNumber"
                                value={formData.phoneNumber}
                                onChange={handleChange}
                                placeholder="+91 9876543210"
                                className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg text-white placeholder-gray-500 focus:border-purple-500 focus:outline-none transition"
                                required
                            />
                            <p className="text-gray-500 text-xs mt-1">Format: +XX XXXXXXXXXX</p>
                        </div>

                        {/* Password */}
                        <div>
                            <label className="block text-gray-300 text-sm font-medium mb-1">
                                Password
                            </label>
                            <input
                                type="password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                placeholder="••••••••"
                                className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg text-white placeholder-gray-500 focus:border-purple-500 focus:outline-none transition"
                                required
                            />
                            <p className="text-gray-500 text-xs mt-1">Minimum 8 characters</p>
                        </div>

                        {/* Confirm Password */}
                        <div>
                            <label className="block text-gray-300 text-sm font-medium mb-1">
                                Confirm Password
                            </label>
                            <input
                                type="password"
                                name="confirmPassword"
                                value={formData.confirmPassword}
                                onChange={handleChange}
                                placeholder="••••••••"
                                className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg text-white placeholder-gray-500 focus:border-purple-500 focus:outline-none transition"
                                required
                            />
                        </div>

                        {/* Date of Birth */}
                        <div>
                            <label className="block text-gray-300 text-sm font-medium mb-1">
                                Date of Birth
                            </label>
                            <input
                                type="date"
                                name="dateOfBirth"
                                value={formData.dateOfBirth}
                                onChange={handleChange}
                                className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg text-white focus:border-purple-500 focus:outline-none transition"
                                required
                            />
                        </div>

                        {/* Country and Gender Row */}
                        <div className="grid grid-cols-2 gap-4">
                            <div>
                                <label className="block text-gray-300 text-sm font-medium mb-1">
                                    Country
                                </label>
                                <select
                                    name="country"
                                    value={formData.country}
                                    onChange={handleChange}
                                    className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg text-white focus:border-purple-500 focus:outline-none transition"
                                >
                                    <option value="INDIA">India</option>
                                    <option value="UNITED_STATES">United States</option>
                                    <option value="UNITED_KINGDOM">United Kingdom</option>
                                </select>
                            </div>

                            <div>
                                <label className="block text-gray-300 text-sm font-medium mb-1">
                                    Gender
                                </label>
                                <select
                                    name="gender"
                                    value={formData.gender}
                                    onChange={handleChange}
                                    className="w-full px-4 py-2 bg-gray-800 border border-gray-700 rounded-lg text-white focus:border-purple-500 focus:outline-none transition"
                                >
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                </select>
                            </div>
                        </div>

                        {/* Submit Button */}
                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full mt-6 bg-gradient-to-r from-purple-600 to-pink-600 hover:from-purple-700 hover:to-pink-700 disabled:from-gray-600 disabled:to-gray-600 text-white font-bold py-2 px-4 rounded-lg transition flex items-center justify-center gap-2"
                        >
                            {loading && <Loader className="w-4 h-4 animate-spin" />}
                            {loading ? 'Creating Account...' : 'Sign Up'}
                        </button>
                    </form>

                    {/* Footer */}
                    <p className="text-center text-gray-500 text-xs mt-6">
                        Connected to backend: localhost:8080
                    </p>
                </div>
            </div>
        </div>
    );
}