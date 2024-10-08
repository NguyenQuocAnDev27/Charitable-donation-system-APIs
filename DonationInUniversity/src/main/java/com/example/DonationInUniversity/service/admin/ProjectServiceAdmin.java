package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.repository.ProjectAdminRepository;
import com.example.DonationInUniversity.model.DonationProject;
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
        try {
            return projectRepository.save(donationProject);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public DonationProject updateProject( DonationProject donationProject) {
        try{
            return projectRepository.save(donationProject);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteProject(int projectId) {
        Optional<DonationProject> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            DonationProject project = projectOptional.get();
            project.setIsDeleted(0); // Đặt isDeleted = 1 để đánh dấu là đã xóa
            projectRepository.save(project);
        }
    }
    public List<DonationProject> adminGetDonationProjectByManager(int id) {
        return projectRepository.adminGetDonationProjectByManager(id);
    }
}
