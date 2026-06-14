package com.wxtkm.taskmanager.repository;

import com.wxtkm.taskmanager.model.Task;
import com.wxtkm.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
}