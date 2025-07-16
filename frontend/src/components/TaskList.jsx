import React from 'react'
import Taskcard from './Taskcard'

/**
 * TaskList component renders a list of tasks using TaskCard components.
 * @param {Object} props - Component props
 * @param {Array} props.tasks - Array of task objects with id, title, description, and status
 * @returns {JSX.Element} A list of TaskCard components
 */
const TaskList = ({ tasks }) => {
    return (
        <div className="container mx-auto p-4">
            {tasks.length > 0 ? (
                <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                {tasks.map((task) => (
                    <Taskcard key={task.id} title={task.title} description={task.description} status={task.status}/>
                ))}
                </div>
            ) : (
                <p className="text-center text-gray-600">No tasks available.</p>
            )}
    </div>
    )
}

export default TaskList