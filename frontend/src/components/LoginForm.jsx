import React, { useState, useContext } from 'react'
import { UserContext } from '../context/UserContext'

/**
 * LoginForm component renders a form for user login with validated username and password fields.
 * @returns {JSX.Element} A login form with input fields, validation, and a submit button
 */
const LoginForm = () => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [errors, setErrors] = useState({ username: '', password: '' })
  const { login, error: apiError } = useContext(UserContext)

  /**
   * Validate form inputs.
   * @returns {boolean} True if valid, false otherwise
   */
  const validateForm = () => {
    const newErrors = { username: '', password: '' }
    let isValid = true

    if (username.length < 3) {
      newErrors.username = 'Username must be at least 3 characters'
      isValid = false
    }

    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/
    if (!passwordRegex.test(password)) {
      newErrors.password = 'Password must be at least 6 characters with one letter and one number'
      isValid = false
    }

    setErrors(newErrors)
    return isValid
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (validateForm()) {
      const success = await login(username, password)
      if (success) {
        setUsername('')
        setPassword('')
        setErrors({ username: '', password: '' })
      }
    }
  }

  return (
    <div className="max-w-md mx-auto bg-white shadow-md rounded-lg p-6 mt-8">
      <h2 className="text-2xl font-bold text-gray-800 mb-6 text-center">Login</h2>
      {apiError && <p className="text-red-500 text-center mb-4">{apiError}</p>}
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label htmlFor="username" className="block text-gray-700 font-medium mb-2">
            Username
          </label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className={`w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 ${
              errors.username ? 'border-red-500' : 'border-gray-300'
            }`}
            placeholder="Enter your username"
            required
          />
          {errors.username && (
            <p className="text-red-500 text-sm mt-1">{errors.username}</p>
          )}
        </div>
        <div className="mb-6">
          <label htmlFor="password" className="block text-gray-700 font-medium mb-2">
            Password
          </label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className={`w-full p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 ${
              errors.password ? 'border-red-500' : 'border-gray-300'
            }`}
            placeholder="Enter your password"
            required
          />
          {errors.password && (
            <p className="text-red-500 text-sm mt-1">{errors.password}</p>
          )}
        </div>
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition-colors"
        >
          Log In
        </button>
      </form>
    </div>
  )
}

export default LoginForm