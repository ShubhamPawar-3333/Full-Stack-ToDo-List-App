package com.portfolio.todolist.ToDoListApplication.service;

import com.portfolio.todolist.ToDoListApplication.dto.UserDTO;
import com.portfolio.todolist.ToDoListApplication.model.User;
import com.portfolio.todolist.ToDoListApplication.repository.UserRepository;
import com.portfolio.todolist.ToDoListApplication.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

/**
 * Service class for user registration, authentication, and user details loading.
 */
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Registers a new user with hashed password.
     *
     * @param userDTO The user data.
     * @return The registered username.
     * @throws IllegalArgumentException if username already exists.
     */
    public String registerUser(UserDTO userDTO){
        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return user.getUsername();
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param userDTO The user credentials.
     * @return The JWT token.
     * @throws IllegalArgumentException if credentials are invalid.
     */
    public String loginUser(UserDTO userDTO){
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Username or password"));
        if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Invalid username or password");
        }
        return jwtTokenProvider.generateToken(user.getUsername());
    }

    /**
     * Loads user details by username for Spring Security.
     *
     * @param username The username.
     * @return The UserDetails object.
     * @throws UsernameNotFoundException if user not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(() -> "ROLE_USER")
        );
    }
}
