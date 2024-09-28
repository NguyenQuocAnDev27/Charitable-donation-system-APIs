package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.service.ProjectService;
import com.example.DonationInUniversity.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<DonationProject> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    public ResponseEntity<DonationProject> createProject(@RequestBody DonationProject project) {
        try {
            return ResponseEntity.ok(projectService.createProject(project));
        } catch (MyException e) {
            throw new MyException("Error creating project: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationProject> getProjectById(@PathVariable int id) {
        DonationProject project = projectService.getProjectById(id);
        if (project == null) {
            throw new MyException("Project not found with id: " + id);
        }
        return ResponseEntity.ok(project);
    }
}
