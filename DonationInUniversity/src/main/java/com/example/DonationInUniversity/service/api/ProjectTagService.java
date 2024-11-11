package com.example.DonationInUniversity.service.api;

import com.example.DonationInUniversity.controller.web.pm.TagsController;
import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.ProjectTag;
import com.example.DonationInUniversity.model.ProjectTagDisplayTable;
import com.example.DonationInUniversity.model.Tag;
import com.example.DonationInUniversity.repository.ProjectRepository;
import com.example.DonationInUniversity.repository.ProjectTagRepository;
import com.example.DonationInUniversity.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectTagService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectTagService.class);

    @Autowired
    ProjectTagRepository projectTagRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    private ProjectRepository projectRepository;

    // Main method to save a Project-Tag association
    @Transactional
    public void saveProjectTag(DonationProject donationProject, Tag tag) {
        Tag existingTag = findOrCreateTag(tag);
        if (!projectTagExists(donationProject.getProjectId(), existingTag.getTagName())) {
            createProjectTag(donationProject, existingTag);
        } else {
            logger.info("ProjectTag already exists for project: {} and tag: {}", donationProject.getProjectName(), existingTag.getTagName());
        }
    }

    // Helper method to find or create a Tag
    private Tag findOrCreateTag(Tag tag) {
        Optional<Tag> existingTagOptional = tagRepository.findByTagName(tag.getTagName());

        if (existingTagOptional.isPresent()) {
            return existingTagOptional.get();
        } else {
            Tag newTag = tagRepository.save(tag);
            logger.info("Tag created: {}", newTag.getTagName());
            return newTag;
        }
    }

    // Helper method to check if Project-Tag association exists
    private boolean projectTagExists(int projectId, String tagName) {
        return projectTagRepository.existsByProjectIdAndTagName(projectId, tagName);
    }

    // Helper method to create a ProjectTag
    private void createProjectTag(DonationProject donationProject, Tag tag) {
        ProjectTag projectTag = new ProjectTag();
        projectTag.setDonationProject(donationProject);
        projectTag.setTag(tag);
        projectTagRepository.save(projectTag);
        logger.info("ProjectTag created for project: {} and tag: {}", donationProject.getProjectName(), tag.getTagName());
    }

    // Save multiple Project-Tag associations
    public void saveProjectTags(DonationProject donationProject, List<Tag> tags) {
        if (tags != null && !tags.isEmpty()) {
            tags.forEach(tag -> saveProjectTag(donationProject, tag));
        } else {
            System.out.println("No tags provided to associate with project: " + donationProject.getProjectName());
        }
    }

    @Transactional
    public void deleteProjectTagByProjectIdAndTagId(Integer projectId, Integer tagId) {
        projectTagRepository.deleteByDonationProject_ProjectIdAndTag_TagId(projectId, tagId);
    }
}
