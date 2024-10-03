package com.example.DonationInUniversity.service;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceAdmin {
    @Autowired
    private ProjectRepository projectRepository;
    public List<DonationProject> getAllProjects() {
        return projectRepository.findByIsDeleted(1);
    }
    public DonationProject getProjectById(int id) {
        return projectRepository.findByProjectId(id);
    }
    public DonationProject addProject(DonationProject donationProject) {
        return projectRepository.save(donationProject);
    }
    public DonationProject updateProject( DonationProject donationProject) {
        return projectRepository.save(donationProject);
    }
    public void deleteProject(int projectId) {
        Optional<DonationProject> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            DonationProject project = projectOptional.get();
            project.setDeleted(0); // Đặt isDeleted = 1 để đánh dấu là đã xóa
            projectRepository.save(project);
        }
    }
}
