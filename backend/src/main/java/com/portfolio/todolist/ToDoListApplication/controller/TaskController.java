package com.portfolio.todolist.ToDoListApplication.controller;

import com.portfolio.todolist.ToDoListApplication.dto.TaskDTO;
import com.portfolio.todolist.ToDoListApplication.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * REST controller for task CRUD operations.
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    /**
     * Service layer for managing task-related operations.
     */
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a new task for the authenticated user.
     *
     * @param taskDTO The task data.
     * @return The created task DTO.
     */
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
//        // Note: User is placeholder; authentication will be implemented later.
//        User user = new User("tempUser", "tempPassword") // temporary for testing
        TaskDTO createdTask = taskService.createTask(taskDTO);
        return ResponseEntity.ok(createdTask);
    }

    /**
     * Retrieves all tasks for the authenticated user.
     *
     * @return List of task DTOs.
     */
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks() {
//        // Note: User is placeholder; authentication will be implemented later.
//        User user = new User("tempUser", "tempPassword") // temporary for testing
        return ResponseEntity.ok(taskService.getTaskByUser());
    }

    /**
     * Retrieves a task by ID.
     *
     * @param id The task ID.
     * @return The task DTO, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.getTaskByID(id);
        if (taskDTO != null) {
            return ResponseEntity.ok(taskDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Updates a task by ID.
     *
     * @param id      The task ID.
     * @param taskDTO The updated task data.
     * @return The updated task DTO, or 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO updatedTask = taskService.updateTask(id, taskDTO);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Deletes a task by ID.
     *
     * @param id The task ID.
     * @return 204 if deleted, 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskService.deleteTask(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
