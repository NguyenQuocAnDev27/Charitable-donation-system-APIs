package com.example.DonationInUniversity.model;
import jakarta.persistence.*;

import java.util.Date;
@Entity
public class DonationProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khóa chính, tự động tăng
    private int projectId;

    private String projectName;
    private String description;
    private double goalAmount;
    private double currentAmount;

    @Temporal(TemporalType.DATE) // Chỉ lưu phần ngày
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String status;

    private int projectManagerId;

    @Temporal(TemporalType.TIMESTAMP) // Lưu cả ngày và giờ
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public DonationProject(int projectId, String projectName, String description, double goalAmount, double currentAmount, Date startDate, Date endDate, String status, int projectManagerId, Date createdAt, Date updatedAt) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.projectManagerId = projectManagerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public DonationProject() {

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

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(int projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
