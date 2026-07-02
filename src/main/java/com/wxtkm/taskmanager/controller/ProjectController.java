package com.wxtkm.taskmanager.controller;

import com.wxtkm.taskmanager.dto.ProjectRequest;
import com.wxtkm.taskmanager.entity.Project;
import com.wxtkm.taskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = {
        "https://portfolio-app-flame-mu.vercel.app",
        "http://localhost:5173"
})
public class ProjectController {

    private final ProjectService projectService;

    @Value("${ADMIN_TOKEN}")
    private String adminToken;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestBody ProjectRequest request,
            @RequestHeader(value = "X-ADMIN-TOKEN", required = false) String token
    ) {

        if (token == null) {
            return ResponseEntity.status(403).body("Missing admin token");
        }

        if (!token.equals(adminToken)) {
            return ResponseEntity.status(403).body("Invalid admin token");
        }

        Project saved = projectService.saveProject(request);

        return ResponseEntity.ok(saved);
    }
}