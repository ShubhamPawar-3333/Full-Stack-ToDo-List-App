import React from 'react'

/**
 * TaskCard component displays a single task's details in a card format.
 * @param {Object} props - Component props
 * @param {string} props.title - The title of the task
 * @param {string} props.description - The description of the task
 * @param {string} props.status - The status of the task (e.g., 'To Do', 'In Progress', 'Done')
 * @returns {JSX.Element} A card displaying task details
 */
const Taskcard = ({ title, description, status }) => {
  return (
    <div className='bg-white shadow-md rounded-lg p-4 mb-4 borderborder-gray-200 hover:shadow-lg transition-shadow'>
        <h3 className='text-lg font-semibold text-gray-800'>{title}</h3>
        <p className='text-gray-600 mt-2'>{description}</p>
        <span
            className={`inline-block mt-3 px-3 py-1 text-sm font-medium rounded-full ${
                status === 'Done'
                ? 'bg-green-100 text-green-100'
                : status === `In Progress`
                ? 'bg-yellow-100 text-yellow-800'
                : 'bg-red-100 text-red-800'
            }`}
            >
                {status}
        </span>
    </div>
  )
}

export default Taskcard