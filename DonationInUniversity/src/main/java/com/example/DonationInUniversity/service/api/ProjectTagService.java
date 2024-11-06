package com.example.DonationInUniversity.service.api;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.ProjectTag;
import com.example.DonationInUniversity.model.ProjectTagDisplayTable;
import com.example.DonationInUniversity.model.Tag;
import com.example.DonationInUniversity.repository.ProjectRepository;
import com.example.DonationInUniversity.repository.ProjectTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectTagService {

    @Autowired
    ProjectTagRepository projectTagRepository;
    @Autowired
    private ProjectRepository projectRepository;

    // Save a single Project-Tag association if it doesn't exist
    public void saveProjectTag(DonationProject donationProject, Tag tag) {
        // Check if the ProjectTag already exists
        if (!projectTagRepository.existsByProjectIdAndTagName(donationProject.getProjectId(), tag.getTagName())) {
            ProjectTag projectTag = new ProjectTag();
            projectTag.setDonationProject(donationProject);
            projectTag.setTag(tag);
            projectTagRepository.save(projectTag);
        } else {
            // Optionally log or handle the case where the project-tag already exists
            System.out.println("ProjectTag already exists for project: " + donationProject.getProjectName() +
                    " and tag: " + tag.getTagName());
        }
    }

    // Save multiple Project-Tag associations
    public void saveProjectTags(DonationProject donationProject, List<Tag> tags) {
        for (Tag tag : tags) {
            saveProjectTag(donationProject, tag);
        }
    }
}
