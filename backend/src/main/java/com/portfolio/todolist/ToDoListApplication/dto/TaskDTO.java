package com.portfolio.todolist.ToDoListApplication.dto;

import com.portfolio.todolist.ToDoListApplication.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Task entity.
 * Used to transfer task data in API requests and responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    /**
     * Unique identifier of the task.
     */
    private Long id;

    /**
     * Title or name of the task.
     */
    private String title;

    /**
     * Detailed description of the task.
     */
    private String description;

    /**
     * Status of the task (e.g., PENDING, IN_PROGRESS, COMPLETED).
     */
    private TaskStatus status;
}
