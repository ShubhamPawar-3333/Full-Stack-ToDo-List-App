import React, { useState } from 'react'
import { Link } from 'react-router-dom'

/**
 * Header component renders a responsive navigation bar with links.
 * @returns {JSX.Element} A header with navigation menu
 */
const Header = () => {
  const [isOpen, setIsOpen] = useState(false)

  return (
    <header className="bg-blue-600 text-white p-4">
      <div className="container mx-auto flex justify-between items-center">
        <h1 className="text-xl font-bold">Todo List</h1>
        <nav>
          <button
            onClick={() => setIsOpen(!isOpen)}
            className="md:hidden focus:outline-none"
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
          <ul className={`${ isOpen ? 'block' : 'hidden' } md:flex md:space-x-4 mt-4 md:mt-0`} >
            <li>
              <Link to="/login" className="hover:underline"> Login </Link>
            </li>
            <li>
              <Link to="/register" className="hover:underline"> Register </Link>
            </li>
            <li>
              <Link to="/tasks" className="hover:underline"> Tasks </Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  )
}

export default Header