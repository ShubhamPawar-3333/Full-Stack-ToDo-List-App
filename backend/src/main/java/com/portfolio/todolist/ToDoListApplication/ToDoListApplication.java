package com.portfolio.todolist.ToDoListApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Todo List Application backend.
 * This class bootstraps the Spring Boot application, enabling auto-configuration,
 * component scanning, and configuration for the RESTful backend.
 */
@SpringBootApplication
public class ToDoListApplication {
    /**
     * Main method to start the Spring Boot application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }
}
