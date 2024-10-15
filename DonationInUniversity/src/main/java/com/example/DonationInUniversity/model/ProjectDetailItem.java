package com.example.DonationInUniversity.model;

public class ProjectDetailItem {
    private String type; // "text" or "image"
    private String content; // Used for text content
    private String path; // Used for image URL
    private int displayOrder;

    public ProjectDetailItem(String type, String content, String path, int displayOrder) {
        this.type = type;
        this.content = content;
        this.path = path;
        this.displayOrder = displayOrder;
    }

    // Getters and Setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}

