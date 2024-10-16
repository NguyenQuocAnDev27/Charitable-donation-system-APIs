package com.example.DonationInUniversity.controller.Api;

import com.example.DonationInUniversity.model.MyCustomResponse;
import com.example.DonationInUniversity.model.OverviewProject;
import com.example.DonationInUniversity.model.PaginatedDonationProjectsResponse;
import com.example.DonationInUniversity.service.api.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project_manager/projects")
public class AdminProjectController {
    private static final Logger logger = LoggerFactory.getLogger(AdminProjectController.class);
    @Autowired
    private ProjectService projectService;

    @GetMapping("/by_page")
    public MyCustomResponse<PaginatedDonationProjectsResponse<OverviewProject>> getAllProjects(
            @RequestParam int projectManagerId,
            @RequestParam(required = false) String searchString,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            PaginatedDonationProjectsResponse<OverviewProject> paginatedProjects = projectService.getAllOverviewProjects(
                    projectManagerId, searchString, page, size);
            return new MyCustomResponse<>(200, "Projects retrieved successfully", paginatedProjects);
        } catch (Exception e) {
            logger.error("Error retrieving projects", e);
            return new MyCustomResponse<>(500, "Error retrieving projects", null);
        }
    }

}
