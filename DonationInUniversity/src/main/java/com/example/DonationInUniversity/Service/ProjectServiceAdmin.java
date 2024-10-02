package com.example.DonationInUniversity.Service;

import com.example.DonationInUniversity.Model.DonationProject;
import com.example.DonationInUniversity.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceAdmin {
    @Autowired
    private ProjectRepository projectRepository;
    public List<DonationProject> getAllProjects() {
        return projectRepository.findAll();
    }
    public DonationProject getProjectById(int id) {
        return projectRepository.findByProjectId(id);
    }
    public DonationProject addProject(DonationProject donationProject) {
        return projectRepository.save(donationProject);
    }
    public DonationProject updateProject(int id, DonationProject donationProject) {
        DonationProject project = projectRepository.findByProjectId(id);
        project.setProjectName(donationProject.getProjectName());
        project.setDescription(donationProject.getDescription());
        project.setCurrentAmount(donationProject.getCurrentAmount());
        project.setGoalAmount(donationProject.getGoalAmount());
        project.setStartDate(donationProject.getStartDate());
        project.setEndDate(donationProject.getEndDate());
        project.setStatus(donationProject.getStatus());
        return projectRepository.save(project);
    }
    public void deleteProject(int id) {
        projectRepository.deleteById(id);
    }
}
