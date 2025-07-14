package com.portfolio.todolist.ToDoListApplication.service;

import com.portfolio.todolist.ToDoListApplication.dto.TaskDTO;
import com.portfolio.todolist.ToDoListApplication.model.Task;
import com.portfolio.todolist.ToDoListApplication.model.User;
import com.portfolio.todolist.ToDoListApplication.repository.TaskRepository;
import com.portfolio.todolist.ToDoListApplication.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing task CRUD operations.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new task for the authenticated user.
     *
     * @param taskDTO The task data transfer object.
     * @return The created task DTO.
     */
    public TaskDTO createTask(TaskDTO taskDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return toDTO(savedTask);
    }

    /**
     * Retrieves all tasks for the authenticated user.
     *
     * @return List of task DTOs.
     */
    @PreAuthorize("hasRole('USER')")
    public List<TaskDTO> getTaskByUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return taskRepository.findAll().stream()
                .filter(task -> task.getUser().getId().equals(user.getId()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a task by ID.
     *
     * @param id The task ID.
     * @return The task DTO, or null if not found.
     */
    @PreAuthorize("hasRole('USER')")
    public TaskDTO getTaskByID(Long id){
        return taskRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    /**
     * Updates an existing task.
     *
     * @param id      The task ID.
     * @param taskDTO The updated task data.
     * @return The updated task DTO, or null if not found.
     */
    @PreAuthorize("hasRole('USER')")
    public TaskDTO updateTask(Long id, TaskDTO taskDTO){
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDTO.getTitle());
                    task.setDescription(taskDTO.getDescription());
                    task.setStatus(taskDTO.getStatus());
                    Task updatedTask = taskRepository.save(task);
                    return toDTO(updatedTask);
                })
                .orElse(null);
    }

    /**
     * Deletes a task by ID.
     *
     * @param id The task ID.
     * @return True if deleted, false if not found.
     */
    @PreAuthorize("hasRole('USER')")
    public boolean deleteTask(Long id){
        boolean idExists = taskRepository.existsById(id);
        if(idExists){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Converts a Task entity to a TaskDTO.
     *
     * @param task The task entity.
     * @return The task DTO.
     */
    private TaskDTO toDTO(Task task){
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }
}
