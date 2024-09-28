package com.example.DonationInUniversity.service;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<DonationProject> getAllProjects() {
        return projectRepository.findAll();
    }

    public DonationProject createProject(DonationProject project) {
        return projectRepository.save(project);
    }

    public DonationProject getProjectById(int id) {
        return projectRepository.findById(id).orElse(null);
    }
}
