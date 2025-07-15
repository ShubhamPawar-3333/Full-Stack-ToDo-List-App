package com.portfolio.todolist.ToDoListApplication.controller;

import com.portfolio.todolist.ToDoListApplication.dto.TaskDTO;
import com.portfolio.todolist.ToDoListApplication.enums.TaskStatus;
import com.portfolio.todolist.ToDoListApplication.exception.GlobalExceptionHandler;
import com.portfolio.todolist.ToDoListApplication.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for TaskController.
 * Tests all task CRUD endpoints, including validation error handling.
 */
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Set up MockMvc with GlobalExceptionHandler for validation errors
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    /**
     * Tests successful task creation with valid input.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void createTask_ValidInput_ReturnsTaskDTO() throws Exception {
        TaskDTO taskDTO = new TaskDTO("Test Task", "Description", TaskStatus.PENDING);
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Task\",\"description\":\"Description\",\"status\":\"PENDING\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(taskService).createTask(any(TaskDTO.class));
    }

    /**
     * Tests task creation with invalid input (empty title).
     */
    @Test
    void createTask_EmptyTitle_ReturnsValidationError() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"description\":\"Description\",\"status\":\"PENDING\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Title is required"));

        verify(taskService, never()).createTask(any(TaskDTO.class));
    }

    /**
     * Tests successful retrieval of all tasks for authenticated user.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void getTaskByUser_ReturnsTaskList() throws Exception {
        TaskDTO taskDTO = new TaskDTO("Test Task", "Description", TaskStatus.PENDING);
        List<TaskDTO> tasks = Collections.singletonList(taskDTO);
        when(taskService.getTaskByUser()).thenReturn(tasks);

        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"))
                .andExpect(jsonPath("$[0].description").value("Description"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));

        verify(taskService).getTaskByUser();
    }

    /**
     * Tests successful retrieval of a task by ID.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void getTaskById_TaskExists_ReturnsTaskDTO() throws Exception {
        TaskDTO taskDTO = new TaskDTO("Test Task", "Description", TaskStatus.PENDING);
        when(taskService.getTaskByID(eq(1L))).thenReturn(taskDTO);

        mockMvc.perform(get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(taskService).getTaskByID(eq(1L));
    }

    /**
     * Tests retrieval of a non-existent task by ID.
     */
    @Test
    void getTaskById_TaskNotFound_Returns404() throws Exception {
        when(taskService.getTaskByID(eq(1L))).thenReturn(null);

        mockMvc.perform(get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(taskService).getTaskByID(eq(1L));
    }

    /**
     * Tests successful task update with valid input.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void updateTask_ValidInput_ReturnsUpdatedTaskDTO() throws Exception {
        TaskDTO taskDTO = new TaskDTO("Updated Task", "Updated Description", TaskStatus.COMPLETED);
        when(taskService.updateTask(eq(1L), any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Task\",\"description\":\"Updated Description\",\"status\":\"COMPLETED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        verify(taskService).updateTask(eq(1L), any(TaskDTO.class));
    }

    /**
     * Tests update of a non-existent task.
     */
    @Test
    void updateTask_TaskNotFound_Returns404() throws Exception {
        when(taskService.updateTask(eq(1L), any(TaskDTO.class))).thenReturn(null);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Task\",\"description\":\"Updated Description\",\"status\":\"COMPLETED\"}"))
                .andExpect(status().isNotFound());

        verify(taskService).updateTask(eq(1L), any(TaskDTO.class));
    }

    /**
     * Tests successful task deletion.
     */
    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void deleteTask_TaskExists_Returns204() throws Exception {
        when(taskService.deleteTask(eq(1L))).thenReturn(true);

        mockMvc.perform(delete("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(eq(1L));
    }

    /**
     * Tests deletion of a non-existent task.
     */
    @Test
    void deleteTask_TaskNotFound_Returns404() throws Exception {
        when(taskService.deleteTask(eq(1L))).thenReturn(false);

        mockMvc.perform(delete("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(taskService).deleteTask(eq(1L));
    }
}
