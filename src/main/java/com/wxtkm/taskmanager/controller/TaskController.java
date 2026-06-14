package com.wxtkm.taskmanager.controller;

import com.wxtkm.taskmanager.dto.TaskRequestDTO;
import com.wxtkm.taskmanager.dto.TaskResponseDTO;
import com.wxtkm.taskmanager.dto.TaskUpdateDTO;
import com.wxtkm.taskmanager.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponseDTO create(@RequestBody TaskRequestDTO dto, Authentication auth) {
        return taskService.createTask(dto, auth.getName());
    }

    @GetMapping
    public List<TaskResponseDTO> myTasks(Authentication auth) {
        return taskService.getMyTasks(auth.getName());
    }

    @PutMapping("/{id}")
    public TaskResponseDTO update(
            @PathVariable Long id,
            @RequestBody TaskUpdateDTO dto,
            Authentication auth
    ) {
        return taskService.updateTask(id, dto, auth.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            Authentication auth
    ) {
        taskService.deleteTask(id, auth.getName());
    }
}
