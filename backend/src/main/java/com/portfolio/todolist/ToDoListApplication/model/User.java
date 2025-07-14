package com.portfolio.todolist.ToDoListApplication.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing the Users table.
 * Stores user information for authentication and associates tasks with users.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * Primary key of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username used for login and identification.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Encrypted password for authentication (max length 60 for BCrypt).
     */
    @Column(nullable = false, length = 60)
    private String password;

    /**
     * List of tasks associated with the user.
     * CascadeType.ALL ensures task operations are tied to user lifecycle.
     * orphanRemoval = true ensures deleted tasks are cleaned from DB.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
}
