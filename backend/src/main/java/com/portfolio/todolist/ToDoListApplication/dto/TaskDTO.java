package com.portfolio.todolist.ToDoListApplication.dto;

import com.portfolio.todolist.ToDoListApplication.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    /**
     * Detailed description of the task.
     */
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    /**
     * Status of the task (e.g., PENDING, IN_PROGRESS, COMPLETED).
     */
    @NotNull(message = "Status is required")
    private TaskStatus status;
}
