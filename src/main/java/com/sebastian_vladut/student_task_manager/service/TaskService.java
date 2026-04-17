package com.sebastian_vladut.student_task_manager.service;

import com.sebastian_vladut.student_task_manager.entity.Task;
import com.sebastian_vladut.student_task_manager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // Create a new task
    public Task createTask(Task task, Long userId) {
        task.setOwnerUserId(userId);
        return taskRepository.save(task);
    }

    // Get tasks for a specific user (ROLE_USER)
    public List<Task> getTasksForUser(Long userId) {
        return taskRepository.findByOwnerUserId(userId);
    }

    // Get all tasks from the system (ROLE_ADMIN)
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Delete a task (ROLE_ADMIN)
    public void deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

}
