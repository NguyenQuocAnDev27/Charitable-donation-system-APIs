package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.ProjectTagDisplayTable;
import com.example.DonationInUniversity.model.Tag;
import com.example.DonationInUniversity.repository.ProjectAdminRepository;
import com.example.DonationInUniversity.model.DonationProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceAdmin {
    @Autowired
    private ProjectAdminRepository projectRepository;
    public Page<DonationProject> getAllProjects(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return projectRepository.findProjectAdminByIsDeleted(1, pageable);
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
    public Page<DonationProject> getAllDonationProjectByManager(int id,int pageNo) {
        Pageable pageable= PageRequest.of(pageNo-1, 5);
        return this.projectRepository.findAll(pageable);
    }
    public DonationProject getDonationProjectById(int id) {
        return this.projectRepository.getDonationProjectByProjectId(id);
    }

    public List<ProjectTagDisplayTable> getAllProjectTags() {
        List<Object[]> results = projectRepository.findAllProjectTags();
        List<ProjectTagDisplayTable> projectTagDisplayTableList = new ArrayList<>();

        for (Object[] result : results) {
            DonationProject project = (DonationProject) result[0];
            Tag tag = (Tag) result[1];

            ProjectTagDisplayTable dto = new ProjectTagDisplayTable(
                    project.getProjectId(),
                    project.getProjectName(),
                    tag.getTagName()
            );

            projectTagDisplayTableList.add(dto);
        }

        return projectTagDisplayTableList;
    }

    public List<ProjectTagDisplayTable> getAllProjectTagsByManager(int managerId, Pageable pageable) {
        // Fetch all project and tag data for the given manager ID
        List<Object[]> results = projectRepository.findAllProjectTagsByManager(managerId, pageable);

        List<ProjectTagDisplayTable> projectTagDisplayTableList = new ArrayList<>();

        for (Object[] result : results) {
            DonationProject project = (DonationProject) result[0];
            Tag tag = (Tag) result[1];

            ProjectTagDisplayTable displayTable = new ProjectTagDisplayTable(
                    project.getProjectId(),
                    project.getProjectName(),
                    tag.getTagName()
            );

            projectTagDisplayTableList.add(displayTable);
        }

        return projectTagDisplayTableList;
    }

    public List<DonationProject> getAllProjectsForManager(int managerId) {
        return projectRepository.findAllProjectsByManager(managerId);
    }

}
