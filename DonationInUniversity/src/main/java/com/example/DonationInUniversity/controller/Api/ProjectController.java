package com.example.DonationInUniversity.controller.Api;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.service.api.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    private ProjectService projectService;

    @GetMapping("/all")
    public MyCustomResponse<List<ProjectTypeDisplay>> getAllProjects() {
        List<DonationProject> projects = projectService.getAllProjects();

        List<ProjectTypeDisplay> filteredProjects = projects.stream().map(project -> {
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

    @GetMapping("/page")
    public MyCustomResponse<PaginatedDonationProjectsResponse<DonationProject>> getProjectsByPage(
            @RequestParam(name = "number", defaultValue = "0") int pageNumber,
            @RequestParam(name = "query", required = false) String searchQuery) {

        // Ensure page number is zero-based and non-negative
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Invalid page number");
        }

        // Retrieve paginated and filtered donation projects based on the search query
        PaginatedDonationProjectsResponse<DonationProject> data =
                projectService.getDonationProjectsByPageAndQuery(pageNumber, searchQuery);

        return new MyCustomResponse<>(200, "Get list project success", data);
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
