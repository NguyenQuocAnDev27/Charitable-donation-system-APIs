package com.example.DonationInUniversity.model;

public class ProjectDetailItem {
    private int id;
    private String type; // "text" or "image"
    private String content; // Used for text content
    private String path; // Used for image URL
    private int displayOrder;
    private int index;

    public ProjectDetailItem(String type, String content, String path, int displayOrder) {
        this.type = type;
        this.content = content;
        this.path = path;
        this.displayOrder = displayOrder;
    }


    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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

