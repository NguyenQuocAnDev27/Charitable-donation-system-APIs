package com.example.DonationInUniversity.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Project_Tags")
public class ProjectTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectTagId;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private DonationProject donationProject;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    // Getters and setters
    public Integer getProjectTagId() {
        return projectTagId;
    }

    public void setProjectTagId(Integer projectTagId) {
        this.projectTagId = projectTagId;
    }

    public DonationProject getDonationProject() {
        return donationProject;
    }

    public void setDonationProject(DonationProject donationProject) {
        this.donationProject = donationProject;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
