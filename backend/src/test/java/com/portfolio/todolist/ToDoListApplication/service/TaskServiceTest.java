package com.portfolio.todolist.ToDoListApplication.service;

import com.portfolio.todolist.ToDoListApplication.dto.TaskDTO;
import com.portfolio.todolist.ToDoListApplication.enums.TaskStatus;
import com.portfolio.todolist.ToDoListApplication.model.Task;
import com.portfolio.todolist.ToDoListApplication.model.User;
import com.portfolio.todolist.ToDoListApplication.repository.TaskRepository;
import com.portfolio.todolist.ToDoListApplication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for TaskService.
 * Tests task CRUD operations with mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    /**
     * Tests successful task creation for authenticated user.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void createTask_ValidInput_ReturnsTaskDTO() {
        TaskDTO taskDTO = new TaskDTO("Test Task", "Description", TaskStatus.PENDING);
        User user = new User(1L, "testuser", "encoded-pass", null);
        Task task = new Task(1L, "Test Task", "Description", TaskStatus.PENDING, user);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDTO result = taskService.createTask(taskDTO);

        assertEquals("Test Task", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());

        verify(taskRepository).save(any(Task.class));
        verify(userRepository).findByUsername("testuser");
    }

    /**
     * Tests task creation when user is not found.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void createTask_UserNotFound_ThrowsException() {
        TaskDTO taskDTO = new TaskDTO("Test Task", "Description", TaskStatus.PENDING);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> taskService.createTask(taskDTO));

        verify(userRepository).findByUsername("testuser");
        verify(taskRepository, never()).save(any(Task.class));
    }

    /**
     * Tests retrieval of tasks for authenticated user.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void getTaskByUser_ReturnsTaskList() {
        User user = new User(1L, "testuser", "encoded-pass", null);
        Task task = new Task(1L, "Test Task", "Description", TaskStatus.PENDING, user);
        List<Task> tasks = Collections.singletonList(task);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskService.getTaskByUser();

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());

        verify(taskRepository).findAll();
        verify(userRepository).findByUsername("testuser");
    }

    /**
     * Tests retrieval of a task by ID.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void getTaskByID_TaskExists_ReturnsTaskDTO() {
        User user = new User(1L, "testuser", "encoded-pass", null);
        Task task = new Task(1L, "Test Task", "Description", TaskStatus.PENDING, user);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.getTaskByID(1L);

        assertEquals("Test Task", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals(TaskStatus.PENDING, result.getStatus());

        verify(taskRepository).findById(1L);
    }

    /**
     * Tests retrieval of a non-existent task by ID.
     */
    @Test
    void getTaskByID_TaskNotFound_ReturnsNull() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskDTO result = taskService.getTaskByID(1L);

        assertNull(result);

        verify(taskRepository).findById(1L);
    }

    /**
     * Tests successful task update.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void updateTask_TaskExists_ReturnsUpdatedTaskDTO() {
        User user = new User(1L, "testuser", "encoded-pass", null);
        Task task = new Task(1L, "Test Task", "Description", TaskStatus.PENDING, user);
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", TaskStatus.COMPLETED, user);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        TaskDTO taskDTO = new TaskDTO("Updated Task", "Updated Description", TaskStatus.COMPLETED);
        TaskDTO result = taskService.updateTask(1L, taskDTO);

        assertEquals("Updated Task", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(TaskStatus.COMPLETED, result.getStatus());

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(any(Task.class));
    }

    /**
     * Tests update of a non-existent task.
     */
    @Test
    void updateTask_TaskNotFound_ReturnsNull() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskDTO taskDTO = new TaskDTO("Updated Task", "Updated Description", TaskStatus.COMPLETED);
        TaskDTO result = taskService.updateTask(1L, taskDTO);

        assertNull(result);

        verify(taskRepository).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    /**
     * Tests successful task deletion.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void deleteTask_TaskExists_ReturnsTrue() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        boolean result = taskService.deleteTask(1L);

        assertTrue(result);

        verify(taskRepository).existsById(1L);
        verify(taskRepository).deleteById(1L);
    }

    /**
     * Tests deletion of a non-existent task.
     */
    @Test
    void deleteTask_TaskNotFound_ReturnsFalse() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        boolean result = taskService.deleteTask(1L);

        assertFalse(result);

        verify(taskRepository).existsById(1L);
        verify(taskRepository, never()).deleteById(1L);
    }
}
