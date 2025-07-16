import React, { createContext, useState, useEffect } from 'react'
import api from '../utils/api'

/**
 * TaskContext provides a context for managing task state across components.
 * @type {React.Context}
 */
export const TaskContext = createContext()

/**
 * TaskProvider wraps the app to provide task-related state and actions.
 * @param {Object} props - Component props
 * @param {React.ReactNode} props.children - Child components
 * @returns {JSX.Element} TaskContext Provider
 */
export const TaskProvider = ({ children }) => {
  const [tasks, setTasks] = useState([])
  const [error, setError] = useState(null)

  /**
   * Fetch all tasks from the backend.
   */
  const fetchTasks = async () => {
    try {
      const response = await api.get('/tasks')
      setTasks(response.data)
      setError(null)
    } catch (err) {
      setError('Failed to fetch tasks')
    }
  }

  /**
   * Add a new task to the backend.
   * @param {Object} task - Task object with title, description, and status
   */
  const addTask = async (task) => {
    try {
      const response = await api.post('/tasks', task)
      setTasks([...tasks, response.data])
      setError(null)
    } catch (err) {
      setError('Failed to add task')
    }
  }

  /**
   * Update an existing task by ID.
   * @param {number} id - Task ID
   * @param {Object} updatedTask - Updated task object
   */
  const updateTask = async (id, updatedTask) => {
    try {
      const response = await api.put(`/tasks/${id}`, updatedTask)
      setTasks(tasks.map((task) => (task.id === id ? response.data : task)))
      setError(null)
    } catch (err) {
      setError('Failed to update task')
    }
  }

  /**
   * Delete a task by ID.
   * @param {number} id - Task ID
   */
  const deleteTask = async (id) => {
    try {
      await api.delete(`/tasks/${id}`)
      setTasks(tasks.filter((task) => task.id !== id))
      setError(null)
    } catch (err) {
      setError('Failed to delete task')
    }
  }

  // Fetch tasks on component mount
  useEffect(() => {
    fetchTasks()
  }, [])

  return (
    <TaskContext.Provider value={{ tasks, addTask, updateTask, deleteTask, error }}>
      {children}
    </TaskContext.Provider>
  )
}