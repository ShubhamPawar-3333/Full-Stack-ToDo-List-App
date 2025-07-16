import React from 'react'
import Header from './components/Header'

const App = () => {
  return (
    <div className='min-h-screen bg-gray-100'>
      <Header />
      <main className='container mx-auto p-4'>
        <h1 className='text-3xl font-bold text-center text-gray-400'>
          Welcome to the ToDo List App
        </h1>
        <p className='text-center text-gray-600 mt-4'>
          Manage your tasks efficiently. Login or register to get started.
        </p>
      </main>
    </div>
  )
}

export default App