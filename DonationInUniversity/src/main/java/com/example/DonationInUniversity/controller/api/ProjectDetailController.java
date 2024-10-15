package com.example.DonationInUniversity.controller.api;


import com.example.DonationInUniversity.model.MyCustomResponse;
import com.example.DonationInUniversity.model.ProjectDetail;
import com.example.DonationInUniversity.service.api.ProjectDetailService;
import com.example.DonationInUniversity.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project_detail")
public class ProjectDetailController {
    private final ProjectDetailService projectDetailService;

    @Autowired
    public ProjectDetailController(ProjectDetailService projectDetailService) {
        this.projectDetailService = projectDetailService;
    }

    @GetMapping("/show")
    public MyCustomResponse<ProjectDetail> getProjectDetailById(@RequestParam Integer projectId) {
        ProjectDetail projectDetail = projectDetailService.getProjectDetails(projectId);

        return new MyCustomResponse<>(
                200,
                "Project details retrieved successfully",
                projectDetail
        );
    }
}
