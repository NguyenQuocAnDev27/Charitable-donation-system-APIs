package com.example.DonationInUniversity.model;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProjectDisplayOverview {
    private Integer projectId;
    private String projectName;
    private String description;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status = "pending";
    private UserProfile projectManager;
    private String imageProject = null;

    public ProjectDisplayOverview() {
    }

    public ProjectDisplayOverview(Integer projectId, String projectName, String description, BigDecimal goalAmount, BigDecimal currentAmount, LocalDate startDate, LocalDate endDate, String status, UserProfile projectManager, String imageProject) {
        this.currentAmount = currentAmount;
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.projectManager = projectManager;
        this.imageProject = imageProject;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public UserProfile getProjectManager() {
        return projectManager;
    }

    public String getImageProject() {
        return imageProject;
    }
}
