package com.portfolio.todolist.ToDoListApplication.controller;

import com.portfolio.todolist.ToDoListApplication.dto.UserDTO;
import com.portfolio.todolist.ToDoListApplication.exception.GlobalExceptionHandler;
import com.portfolio.todolist.ToDoListApplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Unit tests for AuthController.
 * Tests registration and login endpoints, including validation error handling.
 */
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    /**
     * Tests successful user registration with valid input.
     */
    @Test
    void register_ValidInput_ReturnUsername() throws Exception {
        UserDTO userDTO = new UserDTO("testuser", "testpass");
        when(userService.registerUser(any(UserDTO.class))).thenReturn("testuser");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\", \"password\":\"testpass\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("testuser"));

        verify(userService).registerUser(any(UserDTO.class));
    }

    /**
     * Tests registration with invalid input (empty username).
     */
    @Test
    void register_EmptyUsername_ReturnsValidationError() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"\",\"password\":\"testpass\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username is required"));

        verify(userService, never()).registerUser(any(UserDTO.class));
    }

    /**
     * Tests successful login with valid credentials.
     * Uses UserDTO to construct request payload.
     */
    @Test
    void login_ValidCredentials_ReturnsToken() throws Exception {
        UserDTO userDTO = new UserDTO("testuser", "testpass");
        when(userService.loginUser(any(UserDTO.class))).thenReturn("jwt-token");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"testpass\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("jwt-token"));

        verify(userService).loginUser(any(UserDTO.class));
    }

    /**
     * Tests login with invalid input (short password).
     */
    @Test
    void login_ShortPassword_ReturnsValidationError() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"short\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password")
                        .value("Password must be between 6 and 100 characters"));

        verify(userService, never()).loginUser(any(UserDTO.class));
    }
}
