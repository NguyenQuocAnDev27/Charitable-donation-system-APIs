package com.example.DonationInUniversity.service.api;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.OverviewProject;
import com.example.DonationInUniversity.model.PaginatedDonationProjectsResponse;
import com.example.DonationInUniversity.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

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

    public PaginatedDonationProjectsResponse<DonationProject> getDonationProjectsByPageAndQuery(int pageNumber, String searchQuery) {
        int pageSize = 15; // Number of projects per page
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<DonationProject> donationProjectPage;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            // If a search query is provided, find matching projects by name
            donationProjectPage = projectRepository.findByProjectNameContainingIgnoreCase(searchQuery, pageable);
        } else {
            // Otherwise, return all projects for the given page
            donationProjectPage = projectRepository.findAll(pageable);
        }

        // Extract data from the Page object
        List<DonationProject> projects = donationProjectPage.getContent();
        int totalPages = donationProjectPage.getTotalPages();
        int currentPage = donationProjectPage.getNumber();

        // Create and return the paginated response
        return new PaginatedDonationProjectsResponse<>(totalPages, currentPage, projects);
    }

}
