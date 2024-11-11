package com.example.DonationInUniversity.controller.api;


import com.example.DonationInUniversity.model.ContentBlog;
import com.example.DonationInUniversity.model.MyCustomResponse;
import com.example.DonationInUniversity.model.ProjectDetail;
import com.example.DonationInUniversity.model.Tag;
import com.example.DonationInUniversity.repository.ProjectTagRepository;
import com.example.DonationInUniversity.service.api.ProjectDetailService;
import com.example.DonationInUniversity.service.api.ProjectService;
import com.example.DonationInUniversity.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project_detail")
public class ProjectDetailController {
    private final ProjectDetailService projectDetailService;
    private final ProjectTagRepository projectTagRepository;

    @Autowired
    public ProjectDetailController(
            ProjectDetailService projectDetailService,
            ProjectTagRepository projectTagRepository
    ) {
        this.projectDetailService = projectDetailService;
        this.projectTagRepository = projectTagRepository;
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

    @GetMapping("/show_v2")
    public MyCustomResponse<ContentBlog> getContentBlog(@RequestParam Integer projectId) {
        ProjectDetail projectDetail = projectDetailService.getProjectDetails(projectId);
        List<Tag> tags = projectTagRepository.findTagsByProjectId(projectId);
        ContentBlog contentBlog = new ContentBlog();
        contentBlog.setProjectDetail(projectDetail);
        contentBlog.setTags(tags);
        return new MyCustomResponse<>(
                200,
                "Content blog retrieved successfully",
                contentBlog
        );
    }
}
