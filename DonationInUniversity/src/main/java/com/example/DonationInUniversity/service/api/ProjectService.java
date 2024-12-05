package com.example.DonationInUniversity.service.api;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.repository.ProjectDetailImageRepository;
import com.example.DonationInUniversity.repository.ProjectDetailTextRepository;
import com.example.DonationInUniversity.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectDetailImageRepository projectDetailImageRepository;

    public List<DonationProject> getAllProjects() {
        return projectRepository.findAll();
    }

    public PaginatedDonationProjectsResponse<OverviewProject> getAllOverviewProjects(
            int projectManagerId, String searchString, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<DonationProject> donationProjectPage = projectRepository.findByProjectManagerAndSearchString(
                projectManagerId, searchString, pageRequest);

        List<OverviewProject> overviewProjects = donationProjectPage.getContent().stream()
                .map(donationProject -> new OverviewProject(
                        donationProject.getProjectId(),
                        donationProject.getProjectName(),
                        donationProject.getGoalAmount(),
                        donationProject.getCurrentAmount(),
                        donationProject.getStartDate(),
                        donationProject.getEndDate(),
                        donationProject.getStatus()
                ))
                .collect(Collectors.toList());

        return new PaginatedDonationProjectsResponse<>(
                donationProjectPage.getTotalPages(),
                donationProjectPage.getNumber(),
                overviewProjects
        );
    }

    public DonationProject createProject(DonationProject project) {
        return projectRepository.save(project);
    }

    public DonationProject getProjectById(int id) {
        return projectRepository.findById(id).orElse(null);
    }

    public PaginatedDonationProjectsResponse<ProjectDisplayOverview> getDonationProjectsByPageAndSearchKey(int pageNumber, String searchKey) {
        int pageSize = 15; // Number of projects per page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<DonationProject> donationProjectPage;

        if (searchKey != null && !searchKey.isEmpty()) {
            donationProjectPage = projectRepository.findByProjectNameContainingIgnoreCase(searchKey, pageable);
        } else {
            donationProjectPage = projectRepository.findAll(pageable);
        }

        // Extract data from the Page object
        List<DonationProject> projects = donationProjectPage.getContent();
        List<ProjectDisplayOverview> pendingProjects = new ArrayList<>();
        for (DonationProject project : projects) {
            if ("pending".equals(project.getStatus()) || "completed".equals(project.getStatus())) {
                List<ProjectDetailImage> image_list = projectDetailImageRepository.findByProjectId(project.getProjectId());

                String image_link = null;
                if(!image_list.isEmpty()) {
                    image_link = project.getProjectDetailImages().get(0).getPathImage();
                }

                pendingProjects.add(new ProjectDisplayOverview(
                        project.getProjectId(),
                        project.getProjectName(),
                        project.getDescription(),
                        project.getGoalAmount(),
                        project.getCurrentAmount(),
                        project.getStartDate(),
                        project.getEndDate(),
                        project.getStatus(),
                        project.getProjectManager2(),
                        image_link
                ));
            }
        }

        int totalPages = donationProjectPage.getTotalPages();
        int currentPage = donationProjectPage.getNumber();

        // Create and return the paginated response
        return new PaginatedDonationProjectsResponse<>(totalPages, currentPage, pendingProjects);
    }
}
