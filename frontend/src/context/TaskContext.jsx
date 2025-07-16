import React, { createContext, useState} from 'react'

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

    /**
   * Add a new task to the state.
   * @param {Object} task - Task object with id, title, description, and status
   */
    const addTask = (task) => {
        setTasks([...tasks, { ...tasks, id: tasks.length + 1}])
    }

    /**
   * Update an existing task by ID.
   * @param {number} id - Task ID
   * @param {Object} updatedTask - Updated task object
   */
    const updateTask = (id, updatedTask) => {
        setTasks(tasks,map((task) => (task.id === id ? { ...task, ...updateTask } : task)))
    }

    /**
   * Delete a task by ID.
   * @param {number} id - Task ID
   */
    const deleteTask = (id) => {
        setTasks(tasks.filter((task) => task.id !== id))
    }

    return(
        <TaskContext.Provider value={{ tasks, addTask, updateTask, deleteTask}}>
            {children}
        </TaskContext.Provider>
    )
}