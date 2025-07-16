import React, { useState } from 'react'

const Header = () => {
  const [isOpen, setIsOpen] = useState(false)
  
  return (
    <header className='bg-blue-600 text-white p-4'>
        <div className='container mx-auto flex justify-between item-center'>
            <h1 className='text-xl font-bold'>Todo List</h1>
            <nav>
                <button className='md:hidden focus:outline-none' 
                    onClick={() => setIsOpen(!isOpen)}>
                    <svg className='w-6 h-6'
                        fill='none'
                        stroke='currentColor'
                        viewBox='0 0 24 24'
                        xmlns="http://www.w3.org/2000/svg">
                        <path
                            strokeLinecap='round'
                            strokeLinejoin='round'
                            strokeWidth="2"
                            d={isOpen ? 'M6 18L18 6M6 6l12 12' : 'M4 6h16M4 12h16M4 18h16'}
                        />
                    </svg>
                </button>
                <ul className={`${
                    isOpen ? 'block' : 'hidden'
                } md:flex md:space-x-4 mt-4 md:mt-0` }>
                    <li>
                        <a href="/login" className='hover:underline'>Login</a>
                    </li>
                    <li>
                        <a href="/register" className='hover:underline'>Register</a>
                    </li>
                    <li>
                        <a href="/tasks" className='hover:underline'>Tasks</a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
  )
}

export default Header