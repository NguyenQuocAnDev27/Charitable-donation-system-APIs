package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.MyCustomResponse;
import com.example.DonationInUniversity.model.ProjectManagerTypeDisplay;
import com.example.DonationInUniversity.model.ProjectTypeDisplay;
import com.example.DonationInUniversity.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public MyCustomResponse<List<ProjectTypeDisplay>> getAllProjects() {
        List<DonationProject> projects = projectService.getAllProjects();

        List<ProjectTypeDisplay> filteredProjects =  projects.stream().map(project -> {
            ProjectManagerTypeDisplay managerDisplay = new ProjectManagerTypeDisplay(
                    project.getProjectManager().getUserId(),
                    project.getProjectManager().getFullName(),
                    project.getProjectManager().getEmail(),
                    project.getProjectManager().getPhoneNumber()
            );

            return new ProjectTypeDisplay(
                    project.getProjectId(),
                    project.getProjectName(),
                    project.getDescription(),
                    project.getGoalAmount(),
                    project.getCurrentAmount(),
                    project.getStartDate(),
                    project.getEndDate(),
                    project.getStatus(),
                    managerDisplay
            );
        }).collect(Collectors.toList());

        return new MyCustomResponse<>(200, "Get list project success", filteredProjects);
    }

//    @PostMapping
//    public ResponseEntity<DonationProject> createProject(@RequestBody DonationProject project) {
//        try {
//            return ResponseEntity.ok(projectService.createProject(project));
//        } catch (MyException e) {
//            throw new MyException("Error creating project: " + e.getMessage());
//        }
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<DonationProject> getProjectById(@PathVariable int id) {
//        DonationProject project = projectService.getProjectById(id);
//        if (project == null) {
//            throw new MyException("Project not found with id: " + id);
//        }
//        return ResponseEntity.ok(project);
//    }
}
