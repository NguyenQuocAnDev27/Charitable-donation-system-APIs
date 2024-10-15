package com.example.DonationInUniversity.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProjectDetail {
    private Integer projectId;
    private String projectName;
    private String description;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<ProjectDetailItem> projectContent;

    public ProjectDetail() {
    }

    public ProjectDetail(Integer projectId, String projectName, String description, BigDecimal goalAmount, BigDecimal currentAmount, LocalDate startDate, LocalDate endDate, String status, List<ProjectDetailItem> projectContent) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.projectContent = projectContent;
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

    public List<ProjectDetailItem> getProjectContent() {
        return projectContent;
    }

    public void setProjectContent(List<ProjectDetailItem> projectContent) {
        this.projectContent = projectContent;
    }
}

