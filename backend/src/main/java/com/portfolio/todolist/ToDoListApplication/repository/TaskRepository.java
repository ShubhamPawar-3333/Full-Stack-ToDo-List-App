package com.portfolio.todolist.ToDoListApplication.repository;

import com.portfolio.todolist.ToDoListApplication.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA repository for Task entity.
 * Provides CRUD operations for tasks.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
