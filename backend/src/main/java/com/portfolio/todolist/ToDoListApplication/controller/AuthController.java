package com.portfolio.todolist.ToDoListApplication.controller;

import com.portfolio.todolist.ToDoListApplication.dto.UserDTO;
import com.portfolio.todolist.ToDoListApplication.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for user authentication endpoints.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     * Service layer for user registration and authentication.
     */
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     *
     * @param userDTO The user data.
     * @return The registered username.
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserDTO userDTO) {
        String username = userService.registerUser(userDTO);
        Map<String, String> response = new HashMap<>();
        response.put("usernam", username);
        return ResponseEntity.ok(response);
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param userDTO The user credentials.
     * @return The JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserDTO userDTO) {
        String token = userService.loginUser(userDTO);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}
