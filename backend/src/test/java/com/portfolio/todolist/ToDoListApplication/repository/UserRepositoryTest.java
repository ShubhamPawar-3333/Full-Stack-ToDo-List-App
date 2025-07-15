package com.portfolio.todolist.ToDoListApplication.repository;

import com.portfolio.todolist.ToDoListApplication.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for UserRepository.
 * Tests JPA queries using in-memory H2 database.
 */
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Tests finding a user by username when user exists.
     */
    @Test
    void findByUsername_UserExists_ReturnsUser() {
        User user = new User(null, "testuser", "encoded-pass", null);
        userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    /**
     * Tests finding a user by username when user does not exist.
     */
    @Test
    void findByUsername_UserNotFound_ReturnsEmpty() {
        Optional<User> result = userRepository.findByUsername("unknown");

        assertFalse(result.isPresent());
    }
}
