package com.wxtkm.taskmanager.dto;

public class TaskResponseDTO {

    public Long id;
    public String title;
    public String description;
    public String status;

    public TaskResponseDTO(Long id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }
}
