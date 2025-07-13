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
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
}
