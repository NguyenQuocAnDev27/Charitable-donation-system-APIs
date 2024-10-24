package com.example.DonationInUniversity.service.api;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.repository.ProjectDetailImageRepository;
import com.example.DonationInUniversity.repository.ProjectDetailTextRepository;
import com.example.DonationInUniversity.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectDetailService {

    @Value("${server.url}")
    private String serverUrl;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectDetailImageRepository imageRepository;
    @Autowired
    private ProjectDetailTextRepository textRepository;

    public ProjectDetail getProjectDetails(Integer projectId) {
        // Fetch main project details
        DonationProject donationProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        // Initialize content list
        List<ProjectDetailItem> projectContent = new ArrayList<>();

        // Fetch texts with their display order and add to projectContent
        textRepository.findByProjectId(projectId).forEach(text -> {
            projectContent.add(new ProjectDetailItem("text", text.getContent(), null, text.getDisplay_order()));
        });

        // Fetch images with their display order and add to projectContent
        imageRepository.findByProjectId(projectId).forEach(image -> {
            String imageUrl = serverUrl + "/api/project_detail/image/" + image.getId();
            projectContent.add(new ProjectDetailItem("image", null, imageUrl, image.getDisplay_order()));
        });

        // Sort the combined list by displayOrder
        List<ProjectDetailItem> orderedProjectContent = projectContent.stream()
                .sorted(Comparator.comparingInt(ProjectDetailItem::getDisplayOrder))
                .collect(Collectors.toList());

        return new ProjectDetail(
                donationProject.getProjectId(),
                donationProject.getProjectName(),
                donationProject.getDescription(),
                donationProject.getGoalAmount(),
                donationProject.getCurrentAmount(),
                donationProject.getStartDate(),
                donationProject.getEndDate(),
                donationProject.getStatus(),
                orderedProjectContent
        );
    }

}
