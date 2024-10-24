package com.example.DonationInUniversity.model;

public class ProjectTagDisplayTable {
    private Integer projectId;
    private String projectName;
    private String tagName;

    // Constructor
    public ProjectTagDisplayTable(Integer projectId, String projectName, String tagName) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.tagName = tagName;
    }

    // Getters and setters
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
