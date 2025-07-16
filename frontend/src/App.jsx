import React from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Header from './components/Header'
import { TaskProvider } from './context/TaskContext'
import { UserProvider } from './context/UserContext'
import LoginForm from './components/LoginForm'
import RegisterForm from './components/RegisterForm'
import TaskList from './components/TaskList'

/**
 * App component serves as the root component of the Todo List Application.
 * @returns {JSX.Element} The main application layout with routing
 */
const App = () => {
  return (
    <UserProvider>
      <TaskProvider>
        <BrowserRouter>
          <div className="min-h-screen bg-gray-100">
            <Header />
            <main className="container mx-auto p-4">
              <Routes>
                <Route
                  path="/"
                  element={
                    <>
                      <h1 className="text-3xl font-bold text-center text-gray-400">
                        Welcome to the ToDo List App
                      </h1>
                      <p className="text-center text-gray-600 mt-4">
                        Manage your tasks efficiently. Login or register to get started.
                      </p>
                    </>
                  }
                />
                <Route path="/login" element={<LoginForm />} />
                <Route path="/register" element={<RegisterForm />} />
                <Route path="/tasks" element={<TaskList />} />
              </Routes>
            </main>
          </div>
        </BrowserRouter>
      </TaskProvider>
    </UserProvider>
  )
}

export default App