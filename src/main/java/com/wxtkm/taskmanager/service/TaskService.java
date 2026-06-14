package com.wxtkm.taskmanager.service;

import com.wxtkm.taskmanager.dto.TaskRequestDTO;
import com.wxtkm.taskmanager.dto.TaskResponseDTO;
import com.wxtkm.taskmanager.dto.TaskUpdateDTO;
import com.wxtkm.taskmanager.model.Task;
import com.wxtkm.taskmanager.model.User;
import com.wxtkm.taskmanager.repository.TaskRepository;
import com.wxtkm.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponseDTO createTask(TaskRequestDTO dto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Task task = new Task();
        task.setTitle(dto.title);
        task.setDescription(dto.description);
        task.setStatus("TODO");
        task.setUser(user);

        Task saved = taskRepository.save(task);

        return new TaskResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStatus()
        );
    }

    public List<TaskResponseDTO> getMyTasks(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        return taskRepository.findByUserId(user.getId())
                .stream()
                .map(t -> new TaskResponseDTO(
                        t.getId(),
                        t.getTitle(),
                        t.getDescription(),
                        t.getStatus()
                ))
                .toList();
    }

    public TaskResponseDTO updateTask(Long taskId, TaskUpdateDTO dto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Task task = taskRepository.findById(taskId)
                .orElseThrow();

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Forbidden");
        }

        if (dto.title != null) task.setTitle(dto.title);
        if (dto.description != null) task.setDescription(dto.description);
        if (dto.status != null) task.setStatus(dto.status);

        Task saved = taskRepository.save(task);

        return new TaskResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getStatus()
        );
    }

    public void deleteTask(Long taskId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Task task = taskRepository.findById(taskId)
                .orElseThrow();

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Forbidden");
        }

        taskRepository.delete(task);
    }
}