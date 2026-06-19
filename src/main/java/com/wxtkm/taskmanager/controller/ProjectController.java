package com.wxtkm.taskmanager.controller;

import com.wxtkm.taskmanager.dto.ProjectRequest;
import com.wxtkm.taskmanager.entity.Project;
import com.wxtkm.taskmanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public Project createProject(
            @RequestBody ProjectRequest request) {

        return projectService.saveProject(request);
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectService.getAllProjects();
    }
}