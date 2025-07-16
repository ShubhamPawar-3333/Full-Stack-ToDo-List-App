import React, { createContext, useState } from 'react'
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
   * Log in a user via API and update state.
   * @param {string} username - User's username
   * @param {string} password - User's password
   * @returns {Promise<boolean>} True if login succeeds, false otherwise
   */
  const login = async (username, password) => {
    try {
      const response = await api.post('/auth/login', { username, password })
      setUser({ username: response.data.username, isAuthenticated: true })
      setAuthToken(response.data.token)
      setError(null)
      return true
    } catch (err) {
      setError('Login failed: Invalid credentials')
      return false
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
      const response = await api.post('/auth/register', { username, password })
      setUser({ username: response.data.username, isAuthenticated: true })
      setAuthToken(response.data.token)
      setError(null)
      return true
    } catch (err) {
      setError('Registration failed: Username may be taken')
      return false
    }
  }

  /**
   * Log out the user and reset state.
   */
  const logout = () => {
    setUser({ username: '', isAuthenticated: false })
    setAuthToken(null)
    setError(null)
  }

  return (
    <UserContext.Provider value={{ user, login, register, logout, error }}>
      {children}
    </UserContext.Provider>
  )
}