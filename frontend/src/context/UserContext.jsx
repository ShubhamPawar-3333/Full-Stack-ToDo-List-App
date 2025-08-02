import React, { createContext, useState, useEffect } from 'react'
import api, { setAuthToken } from '../utils/api'

/**
 * UserContext provides a context for managing user authentication state.
 * @type {React.Context}
 */
export const UserContext = createContext()

/**
 * UserProvider wraps the app to provide user-related state and actions.
 * @param {Object} props - Component props
 * @param {React.ReactNode} props.children - Child components
 * @returns {JSX.Element} UserContext Provider
 */
export const UserProvider = ({ children }) => {
  const [user, setUser] = useState({ username: '', isAuthenticated: false })
  const [error, setError] = useState(null)

  /**
   * Restore JWT token from localStorage on mount.
   */
  useEffect(() => {
    const storedToken = localStorage.getItem('jwtToken');
    if (storedToken) {
      setAuthToken(storedToken);
      setUser((prev) => ({ ...prev, isAuthenticated: true }));
      console.debug('Restored JWT token from localStorage');
    }
  }, []);

  /**
   * Log in a user via API and update state.
   * @param {string} username - User's username
   * @param {string} password - User's password
   * @returns {Promise<boolean>} True if login succeeds, false otherwise
   */
  const login = async (username, password) => {
    try {
      const response = await api.post('/auth/login', { username, password });
      setUser({ username: response.data.username, isAuthenticated: true });
      setAuthToken(response.data.token);
      localStorage.setItem('jwtToken', response.data.token);
      setError(null);
      console.debug('Login successful, token stored');
      return true;
    } catch (err) {
      const errorMessage = err.response?.data ? JSON.stringify(err.response.data) : 'Login failed: Invalid credentials';
      console.error('Login error:', errorMessage);
      setError(errorMessage);
      return false;
    }
  }

  /**
   * Register a user via API and update state.
   * @param {string} username - User's username
   * @param {string} password - User's password
   * @returns {Promise<boolean>} True if registration succeeds, false otherwise
   */
  const register = async (username, password) => {
    try {
      const response = await api.post('/auth/register', { username, password });
      setUser({ username: response.data.username, isAuthenticated: true });
      setAuthToken(response.data.token);
      localStorage.setItem('jwtToken', response.data.token);
      setError(null);
      console.debug('Registration successful, token stored');
      return true;
    } catch (err) {
      const errorMessage = err.response?.data ? JSON.stringify(err.response.data) : 'Registration failed: Username may be taken';
      console.error('Registration error:', errorMessage);
      setError(errorMessage);
      return false;
    }
  }

  /**
   * Log out the user and reset state.
   */
  const logout = () => {
    setUser({ username: '', isAuthenticated: false });
    setAuthToken(null);
    localStorage.removeItem('jwtToken');
    setError(null);
    console.debug('Logged out, token cleared');
  }

  return (
    <UserContext.Provider value={{ user, login, register, logout, error }}>
      {children}
    </UserContext.Provider>
  )
}