package com.example.DonationInUniversity.service.api;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.Tag;
import com.example.DonationInUniversity.model.ProjectTag;
import com.example.DonationInUniversity.repository.TagRepository;
import com.example.DonationInUniversity.repository.ProjectTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProjectTagRepository projectTagRepository;

    @Autowired
    private ProjectService projectService;

    // Fetch all tags for projects managed by a specific manager
    public Page<Tag> getAllTagsByManager(int managerId, Pageable pageable) {
        // Get all tags associated with the manager's projects via ProjectTag
        return projectTagRepository.findTagsByProjectManager(managerId, pageable);
    }

    // Add a new tag
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Update an existing tag
    public Tag updateTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Delete a tag
    public void deleteTag(int tagId) {
        tagRepository.deleteById(tagId);
    }

    public void saveTagWithProjectTag(Tag tag, Integer projectId) {
        // Fetch the DonationProject by its ID
        DonationProject project = projectService.getProjectById(projectId);

        if (project == null) {
            throw new IllegalArgumentException("Project not found");
        }

        // Save the tag first (in Tags table)
        tagRepository.save(tag);

        // Create a ProjectTag association
        ProjectTag projectTag = new ProjectTag();
        projectTag.setDonationProject(project);
        projectTag.setTag(tag);

        // Save the project-tag association (in Project_Tags table)
        projectTagRepository.save(projectTag);
    }

    public Tag findOrCreateByName(String tagName) {
        Optional<Tag> existingTag = tagRepository.findByTagName(tagName);
        if (existingTag.isPresent()) {
            return existingTag.get();
        } else {
            Tag newTag = new Tag();
            newTag.setTagName(tagName);
            return tagRepository.save(newTag);
        }
    }
}
