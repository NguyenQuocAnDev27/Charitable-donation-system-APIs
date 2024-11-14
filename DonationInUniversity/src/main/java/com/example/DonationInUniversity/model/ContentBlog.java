package com.example.DonationInUniversity.model;

import java.util.List;

public class ContentBlog {
    private ProjectDetail projectDetail;
    private List<Tag> tags;

    public ContentBlog() {
    }

    public ContentBlog(ProjectDetail projectDetail, List<Tag> tags) {
        this.projectDetail = projectDetail;
        this.tags = tags;
    }

    public ProjectDetail getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(ProjectDetail projectDetail) {
        this.projectDetail = projectDetail;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
