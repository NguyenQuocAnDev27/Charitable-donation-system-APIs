package com.example.DonationInUniversity.model;

public class ProjectTagDisplayTable {
    private Integer projectId;
    private String projectName;
    private Integer tagId;
    private String tagName;
    private Integer projectTagId;

    public ProjectTagDisplayTable() {
    }

    public ProjectTagDisplayTable(Integer projectId, String projectName, Integer tagId, String tagName, Integer projectTagId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.tagId = tagId;
        this.tagName = tagName;
        this.projectTagId = projectTagId;
    }

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

    public Integer getProjectTagId() {
        return projectTagId;
    }

    public void setProjectTagId(Integer projectTagId) {
        this.projectTagId = projectTagId;
    }
}
