package com.example.DonationInUniversity.model;

public class ProjectTag {
    private int projectTagId;
    private int projectId;
    private int tagId;

    public ProjectTag(int projectTagId, int projectId, int tagId) {
        this.projectTagId = projectTagId;
        this.projectId = projectId;
        this.tagId = tagId;
    }

    // Getters and Setters
    public int getProjectTagId() {
        return projectTagId;
    }

    public void setProjectTagId(int projectTagId) {
        this.projectTagId = projectTagId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
