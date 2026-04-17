package com.sebastian_vladut.student_task_manager.controller;

import com.sebastian_vladut.student_task_manager.entity.Task;
import com.sebastian_vladut.student_task_manager.entity.User;
import com.sebastian_vladut.student_task_manager.repository.UserRepository;
import com.sebastian_vladut.student_task_manager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    // Extract the username from the Basic Authentification and search the ID in the database
    private Long getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));
        return user.getId();
    }

    // Create a new task (USER/ADMIN)
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, Authentication authentication) {
        Long userId = getUserId(authentication);
        Task createdTask = taskService.createTask(task, userId);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // Get tasks of the logged-in user (USER/ADMIN)
    @GetMapping("/my")
    public ResponseEntity<List<Task>> getUserTasks(Authentication authentication) {
        Long userId = getUserId(authentication);
        List<Task> tasks = taskService.getTasksForUser(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // List all tasks (ADMIN ONLY)
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(Authentication authentication) {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    // Delete any task (ADMIN ONLY)
    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
