package com.example.DonationInUniversity.model;

import java.util.List;

public class ProjectDetailForm {
    private List<ProjectDetailImage> newListImage;
    private List<ProjectDetailText> newListText;

    // Getters and setters
    public List<ProjectDetailImage> getNewListImage() {
        return newListImage;
    }

    public void setNewListImage(List<ProjectDetailImage> newListImage) {
        this.newListImage = newListImage;
    }

    public List<ProjectDetailText> getNewListText() {
        return newListText;
    }

    public void setNewListText(List<ProjectDetailText> newListText) {
        this.newListText = newListText;
    }
}

