package com.portfolio.todolist.ToDoListApplication.service;

import com.portfolio.todolist.ToDoListApplication.dto.TaskDTO;
import com.portfolio.todolist.ToDoListApplication.model.Task;
import com.portfolio.todolist.ToDoListApplication.model.User;
import com.portfolio.todolist.ToDoListApplication.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Creates a new task for a user.
     *
     * @param taskDTO The task data transfer object.
     * @param user    The user creating the task.
     * @return The created task DTO.
     */

    public TaskDTO createTask(TaskDTO taskDTO, User user){
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return toDTO(savedTask);
    }

    /**
     * Retrieves all tasks for a user.
     *
     * @param user The user whose tasks are retrieved.
     * @return List of task DTOs.
     */
    public List<TaskDTO> getTaskByUser(User user){
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
