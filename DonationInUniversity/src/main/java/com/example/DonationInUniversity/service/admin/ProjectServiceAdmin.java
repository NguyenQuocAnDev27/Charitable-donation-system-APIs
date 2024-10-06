package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.repository.ProjectAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceAdmin {
    @Autowired
    private ProjectAdminRepository projectRepository;
    public List<DonationProject> getAllProjects() {
        return projectRepository.findProjectAdminByIsDeleted(1);
    }
    public Optional<DonationProject> getProjectById(int id) {
        return projectRepository.findById(id);
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
            project.setIsDeleted(0); // Đặt isDeleted = 1 để đánh dấu là đã xóa
            projectRepository.save(project);
        }
    }
}
