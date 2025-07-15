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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for TaskController.
 * Tests task CRUD endpoints, including validation error handling.
 */
@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Set up MockMvc with GlobalExceptionHandler for validation errors
        mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        // Mock SecurityContextHolder for authenticated user to satisfy @PreAuthorize
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        SecurityContextHolder.setContext(securityContext);
    }

    /**
     * Tests successful task creation with valid input.
     */
    @Test
    void createTask_ValidInput_ReturnsTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO("Test task", "Description", TaskStatus.PENDING);
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(taskDTO);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Task\",\"description\":\"Description\",\"status\":\"PENDING\"}"))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(taskService).createTask(any(TaskDTO.class));
    }

    /**
     * Tests task creation with invalid input (empty title).
     */
    @Test
    void createTask_EmptyTitle_ReturnValidationError() throws Exception {
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
    void getTaskByUser_ReturnsTaskList() throws Exception {
        TaskDTO taskDTO = new TaskDTO("Test Task", "Description", TaskStatus.PENDING);
        List<TaskDTO> tasks = Collections.singletonList(taskDTO);
        when(taskService.getTaskByUser()).thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"))
                .andExpect(jsonPath("$[0].description").value("Description"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));

        verify(taskService).getTaskByUser();
    }
}
