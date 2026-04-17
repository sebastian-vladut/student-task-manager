package com.sebastian_vladut.student_task_manager.service;

import com.sebastian_vladut.student_task_manager.entity.Task;
import com.sebastian_vladut.student_task_manager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository; // Mock database

    @InjectMocks
    private TaskService taskService; // The service tested

    @Test
    void createTask() {
        Task task = new Task(); // Mock Task
        task.setTitle("Test Task"); // Mock Task Title
        Long userId = 6L; // Mock user id

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task savedTask = taskService.createTask(task, userId);

        assertNotNull(savedTask);
        assertEquals(6L, task.getOwnerUserId());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void getTasksForUser() {
        Long userId = 6L; // Mock user id
        when(taskRepository.findByOwnerUserId(userId)).thenReturn(List.of(new Task(), new Task())); // Mock list of tasks for the mock user

        List<Task> tasks = taskService.getTasksForUser(userId);

        assertEquals(2, tasks.size()); // Test if it successfully retrieves the list of mock tasks
        verify(taskRepository, times(1)).findByOwnerUserId(userId);
    }

}
