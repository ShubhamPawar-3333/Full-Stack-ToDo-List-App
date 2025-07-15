package com.portfolio.todolist.ToDoListApplication.service;

import com.portfolio.todolist.ToDoListApplication.dto.UserDTO;
import com.portfolio.todolist.ToDoListApplication.model.User;
import com.portfolio.todolist.ToDoListApplication.repository.UserRepository;
import com.portfolio.todolist.ToDoListApplication.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserService.
 * Tests user registration, login, and UserDetails loading with mocked dependencies.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Initialize any setup if needed
    }

    /**
     * Tests successful user registration.
     */
    @Test
    void registerUser_ValidInput_ReturnsUsername() {
        UserDTO userDTO = new UserDTO("testuser", "testpass");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testpass")).thenReturn("encoded-pass");
        when(userRepository.save(any(User.class))).thenReturn(new User(1L, "testuser", "encoded-pass", null));

        String result = userService.registerUser(userDTO);

        assertEquals("testuser", result);
        verify(passwordEncoder).encode("testpass");
        verify(userRepository).save(any(User.class));
    }

    /**
     * Tests registration with existing username.
     */
    @Test
    void registerUser_UsernameExists_ThrowsException() {
        UserDTO userDTO = new UserDTO("testuser", "testpass");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userDTO));
        verify(userRepository).findByUsername("testuser");
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Tests successful user login.
     */
    @Test
    void loginUser_ValidCredentials_ReturnsToken() {
        UserDTO userDTO = new UserDTO("testuser", "testpass");
        User user = new User(1L, "testuser", "encoded-pass", null);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("testpass", "encoded-pass")).thenReturn(true);
        when(jwtTokenProvider.generateToken("testuser")).thenReturn("jwt-token");

        String result = userService.loginUser(userDTO);

        assertEquals("jwt-token", result);
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("testpass", "encoded-pass");
        verify(jwtTokenProvider).generateToken("testuser");
    }

    /**
     * Tests login with invalid username.
     */
    @Test
    void loginUser_InvalidUsername_ThrowsException() {
        UserDTO userDTO = new UserDTO("testuser", "testpass");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.loginUser(userDTO));
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder, never()).matches(any(), any());
    }

    /**
     * Tests login with invalid password.
     */
    @Test
    void loginUser_InvalidPassword_ThrowsException() {
        UserDTO userDTO = new UserDTO("testuser", "wrongpass");
        User user = new User(1L, "testuser", "encoded-pass", null);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "encoded-pass")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.loginUser(userDTO));
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("wrongpass", "encoded-pass");
    }

    /**
     * Tests loading UserDetails by username when user exists.
     */
    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        User user = new User(1L, "testuser", "encoded-pass", null);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encoded-pass", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
        verify(userRepository).findByUsername("testuser");
    }

    /**
     * Tests loading UserDetails for non-existent user.
     */
    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.loadUserByUsername("unknown"));
        verify(userRepository).findByUsername("unknown");
    }
}
