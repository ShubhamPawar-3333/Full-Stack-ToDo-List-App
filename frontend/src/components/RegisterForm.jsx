import React, { useState, useContext } from 'react'
import { UserContext } from '../context/UserContext'

/**
 * RegisterForm component renders a form for user registration with username and password fields.
 * @returns {JSX.Element} A registration form with input fields and a submit button
 */
const RegisterForm = () => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const { login } = useContext(UserContext) // Using login to simulate registration

  const handleSubmit = (e) => {
    e.preventDefault()
    // Simulate registration (to be replaced with API call in Phase 4)
    login(username) // Temporarily use login to update user state
    setUsername('')
    setPassword('')
  }

  return (
    <div className="max-w-md mx-auto bg-white shadow-md rounded-lg p-6 mt-8">
      <h2 className="text-2xl font-bold text-gray-800 mb-6 text-center">Register</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label htmlFor="username" className="block text-gray-700 font-medium mb-2"> Username </label>
          <input type="text" id="username" value={username} placeholder="Enter your username"
            onChange={(e) => setUsername(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            required/>
        </div>
        <div className="mb-6">
          <label htmlFor="password" className="block text-gray-700 font-medium mb-2"> Password </label>
          <input type="password" id="password" value={password}
            onChange={(e) => setPassword(e.target.value)} placeholder="Enter your password"
            className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            required/>
        </div>
        <button type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition-colors"
          >
            Register
        </button>
      </form>
    </div>
  )
}

export default RegisterForm