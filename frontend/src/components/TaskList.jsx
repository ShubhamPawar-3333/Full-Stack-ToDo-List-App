import React, { useState, useContext } from 'react'
import TaskCard from './TaskCard'
import { TaskContext } from '../context/TaskContext'

/**
 * TaskList component renders a list of tasks with a form to add new tasks.
 * @returns {JSX.Element} A task list with add/update/delete functionality
 */
const TaskList = () => {
  const { tasks, addTask, updateTask, deleteTask, error } = useContext(TaskContext)
  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [status, setStatus] = useState('To Do')

  const handleAddTask = async (e) => {
    e.preventDefault()
    await addTask({ title, description, status })
    setTitle('')
    setDescription('')
    setStatus('To Do')
  }

  const handleUpdateTask = (id, task) => {
    updateTask(id, task)
  }

  const handleDeleteTask = (id) => {
    deleteTask(id)
  }

  return (
    <div className="container mx-auto p-4">
      <h2 className="text-2xl font-bold text-gray-800 mb-6 text-center">Your Tasks</h2>
      {error && <p className="text-red-500 text-center mb-4">{error}</p>}
      <form onSubmit={handleAddTask} className="mb-8 max-w-md mx-auto">
        <div className="mb-4">
          <label htmlFor="title" className="block text-gray-700 font-medium mb-2"> Task Title </label>
          <input type="text" id="title" value={title} placeholder="Enter task title"
            onChange={(e) => setTitle(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            required/>
        </div>
        <div className="mb-4">
          <label htmlFor="description" className="block text-gray-700 font-medium mb-2"> Description </label>
          <textarea id="description" value={description} placeholder="Enter task description"
            onChange={(e) => setDescription(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"/>
        </div>
        <div className="mb-4">
          <label htmlFor="status" className="block text-gray-700 font-medium mb-2"> Status </label>
          <select id="status" value={status}
            onChange={(e) => setStatus(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
            <option value="To Do">To Do</option>
            <option value="In Progress">In Progress</option>
            <option value="Done">Done</option>
          </select>
        </div>
        <button type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition-colors">
          Add Task
        </button>
      </form>
      {tasks.length > 0 ? (
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
          {tasks.map((task) => (
            <div key={task.id}>
              <TaskCard
                title={task.title}
                description={task.description}
                status={task.status}/>
              <div className="flex justify-between mt-2">
                <button
                  onClick={() =>
                    handleUpdateTask(task.id, {
                      ...task,
                      status: task.status === 'Done' ? 'In Progress' : 'Done',
                    })
                  }
                  className="bg-yellow-500 text-white px-3 py-1 rounded-md hover:bg-yellow-600">
                  Toggle Status
                </button>
                <button
                  onClick={() => handleDeleteTask(task.id)}
                  className="bg-red-500 text-white px-3 py-1 rounded-md hover:bg-red-600">
                  Delete
                </button>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <p className="text-center text-gray-600">No tasks available.</p>
      )}
    </div>
  )
}

export default TaskList