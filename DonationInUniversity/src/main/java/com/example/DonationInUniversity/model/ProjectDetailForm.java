package com.example.DonationInUniversity.model;

import java.util.List;

public class ProjectDetailForm {
    private List<ProjectDetailImageAdmin> newListImage;
    private List<ProjectDetailTextAdmin> newListText;

    // Getters and setters
    public List<ProjectDetailImageAdmin> getNewListImage() {
        return newListImage;
    }

    public void setNewListImage(List<ProjectDetailImageAdmin> newListImage) {
        this.newListImage = newListImage;
    }

    public List<ProjectDetailTextAdmin> getNewListText() {
        return newListText;
    }

    public void setNewListText(List<ProjectDetailTextAdmin> newListText) {
        this.newListText = newListText;
    }
}

