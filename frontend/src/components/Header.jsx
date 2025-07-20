import React, { useState } from 'react'
import { Link } from 'react-router-dom'

/**
 * Header component renders a responsive navigation bar with a hamburger menu for mobile devices.
 * @returns {JSX.Element} A header with navigation menu
 */
const Header = () => {
  const [isOpen, setIsOpen] = useState(false)

  return (
    <header className="bg-blue-600 text-white py-3 sm:py-4">
      <div className="container mx-auto px-4 flex justify-between items-center">
        <h1 className="text-lg sm:text-xl md:text-2xl font-bold">Todo List</h1>
        <nav>
          <button
            className="md:hidden focus:outline-none p-2"
            onClick={() => setIsOpen(!isOpen)}
          >
            <svg
              className="w-6 h-6"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d={isOpen ? 'M6 18L18 6M6 6l12 12' : 'M4 6h16M4 12h16M4 18h16'}
              />
            </svg>
          </button>
          <ul
            className={`${
              isOpen ? 'flex' : 'hidden'
            } flex-col md:flex md:flex-row md:space-x-6 mt-4 md:mt-0 bg-blue-600 md:bg-transparent absolute md:static top-14 left-0 right-0 px-4 pb-4 md:p-0`}
          >
            <li>
              <Link to="/login" className="block py-2 md:py-0 hover:underline text-base md:text-lg">
                Login
              </Link>
            </li>
            <li>
              <Link to="/register" className="block py-2 md:py-0 hover:underline text-base md:text-lg">
                Register
              </Link>
            </li>
            <li>
              <Link to="/tasks" className="block py-2 md:py-0 hover:underline text-base md:text-lg">
                Tasks
              </Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  )
}

export default Header