package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.controller.web.pm.ProjectManagerController;
import com.example.DonationInUniversity.model.ProjectTagDisplayTable;
import com.example.DonationInUniversity.repository.ProjectAdminRepository;
import com.example.DonationInUniversity.model.DonationProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceAdmin {
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceAdmin.class);
    @Autowired
    private ProjectAdminRepository projectRepository;
    public Page<DonationProject> getAllProjects(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo-1, 5);
        return projectRepository.findProjectAdminByIsDeleted(1,pageable);
    }
    public Optional<DonationProject> getProjectById(int id) {
        return projectRepository.findById(id);
    }
    public void addProject(DonationProject donationProject) {
        try {
            projectRepository.save(donationProject);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateProject(DonationProject donationProject) {
        try{
            projectRepository.save(donationProject);
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
    public Page<DonationProject> getAllDonationProjectByManager(int id,int pageNo) {
        Pageable pageable= PageRequest.of(pageNo-1, 5);
        return this.projectRepository.findAllByManagerId(id,pageable);
    }
    public DonationProject getDonationProjectById(int id) {
        return this.projectRepository.getDonationProjectByProjectId(id);
    }

    public List<ProjectTagDisplayTable> getAllProjectTagsByManager(int managerId) {
        // Fetch all project and tag data for the given manager ID
        List<Object[]> results = projectRepository.findAllProjectTagsByManager(managerId);

        List<ProjectTagDisplayTable> projectTagDisplayTableList = new ArrayList<>();

        for (Object[] result : results) {
            Integer projectId = (Integer) result[0];
            String projectName = (String) result[1];
            Integer tagId = (Integer) result[2];
            String tagName = (String) result[3];
            Integer projectTagId = (Integer) result[4];

            ProjectTagDisplayTable displayTable = new ProjectTagDisplayTable(projectId, projectName, tagId, tagName, projectTagId);

            projectTagDisplayTableList.add(displayTable);
        }

        return projectTagDisplayTableList;
    }

    public List<DonationProject> getAllProjectsForManager(int managerId) {
        return projectRepository.findAllProjectsByManager(managerId);
    }
    public int getProjectStatus(String status){
        return projectRepository.findProjectStatus(status).size();
    }
    public int totalAmountByStatuses(String... statuses) {
        return projectRepository.findAll().stream()
                .filter(project -> Arrays.asList(statuses).contains(project.getStatus()))
                .map(DonationProject::getCurrentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .intValue();
    }
}
