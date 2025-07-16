import React, { createContext, useState } from 'react'

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

  /**
   * Log in a user and update state.
   * @param {string} username - User's username
   */
  const login = (username) => {
    setUser({ username, isAuthenticated: true })
  }

  /**
   * Log out the user and reset state.
   */
  const logout = () => {
    setUser({ username: '', isAuthenticated: false })
  }

  return (
    <UserContext.Provider value={{ user, login, logout }}>
      {children}
    </UserContext.Provider>
  )
}