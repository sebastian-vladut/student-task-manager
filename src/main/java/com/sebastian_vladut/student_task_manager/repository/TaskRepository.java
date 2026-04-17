package com.sebastian_vladut.student_task_manager.repository;

import com.sebastian_vladut.student_task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwnerUserId(Long ownerUserId);
}
