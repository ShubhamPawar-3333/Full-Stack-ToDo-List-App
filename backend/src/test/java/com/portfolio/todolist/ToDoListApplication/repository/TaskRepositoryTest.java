package com.portfolio.todolist.ToDoListApplication.repository;

import com.portfolio.todolist.ToDoListApplication.enums.TaskStatus;
import com.portfolio.todolist.ToDoListApplication.model.Task;
import com.portfolio.todolist.ToDoListApplication.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for TaskRepository.
 * Tests JPA CRUD operations using in-memory H2 database.
 */
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Tests saving and retrieving a task.
     */
    @Test
    void saveAndFindTask_SavesAndReturnsTask() {
        User user = new User(null, "testuser", "encoded-pass", null);
        user = userRepository.save(user);
        Task task = new Task(null, "Test Task", "Description", TaskStatus.PENDING, user);

        taskRepository.save(task);
        List<Task> tasks = taskRepository.findAll();

        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }

    /**
     * Tests checking existence of a task by ID.
     */
    @Test
    void existsById_TaskExists_ReturnsTrue() {
        User user = new User(null, "testuser", "encoded-pass", null);
        user = userRepository.save(user);
        Task task = new Task(null, "Test Task", "Description", TaskStatus.PENDING, user);
        Task savedTask = taskRepository.save(task);

        boolean exists = taskRepository.existsById(savedTask.getId());

        assertTrue(exists);
    }

    /**
     * Tests checking existence of a non-existent task by ID.
     */
    @Test
    void existsById_TaskNotFound_ReturnsFalse() {
        boolean exists = taskRepository.existsById(1L);

        assertFalse(exists);
    }
}
