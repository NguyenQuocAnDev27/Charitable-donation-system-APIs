package com.example.DonationInUniversity.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProjectTypeDisplay {
    private int projectId;
    private String projectName;
    private String description;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private ProjectManagerTypeDisplay projectManager; // Nested class for project manager

    // Constructors, Getters, and Setters

    public ProjectTypeDisplay(int projectId, String projectName, String description, BigDecimal goalAmount,
                              BigDecimal currentAmount, LocalDate startDate, LocalDate endDate, String status,
                              ProjectManagerTypeDisplay projectManager) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.projectManager = projectManager;
    }

    // Getters and Setters

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProjectManagerTypeDisplay getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManagerTypeDisplay projectManager) {
        this.projectManager = projectManager;
    }
}

