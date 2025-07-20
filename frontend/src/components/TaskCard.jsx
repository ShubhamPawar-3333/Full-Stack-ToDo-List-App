import React from 'react'

/**
 * TaskCard component displays a single task with title, description, and status.
 * @param {Object} props - Component props
 * @param {string} props.title - Task title
 * @param {string} props.description - Task description
 * @param {string} props.status - Task status (To Do, In Progress, Done)
 * @returns {JSX.Element} A task card with styled content
 */
const TaskCard = ({ title, description, status }) => {
  const statusColor =
    status === 'To Do'
      ? 'bg-red-100 text-red-800'
      : status === 'In Progress'
      ? 'bg-yellow-100 text-yellow-800'
      : 'bg-green-100 text-green-800'

  return (
    <div className="bg-white shadow-md rounded-lg p-4 sm:p-5 hover:shadow-lg transition-shadow w-full max-w-sm mx-auto">
      <h3 className="text-lg sm:text-xl font-semibold text-gray-800 mb-2">{title}</h3>
      <p className="text-gray-600 text-sm sm:text-base mb-3">{description || 'No description'}</p>
      <span
        className={`inline-block px-3 py-1 rounded-full text-xs sm:text-sm font-medium ${statusColor}`}
      >
        {status}
      </span>
    </div>
  )
}

export default TaskCard