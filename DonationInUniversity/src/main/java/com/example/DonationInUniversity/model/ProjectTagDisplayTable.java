package com.example.DonationInUniversity.model;

public class ProjectTagDisplayTable {
    private Integer projectId;
    private String projectName;
    private Integer tagId;
    private String tagName;

    // Constructor
    public ProjectTagDisplayTable(Integer projectId, String projectName, Integer tagId, String tagName) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.tagId = tagId;
        this.tagName = tagName;
    }

    // Getters and Setters
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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
