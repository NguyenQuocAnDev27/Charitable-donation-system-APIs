package com.example.DonationInUniversity.Model;
import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "donation_projects")
public class DonationProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;
    @Column(nullable = false)
    private String projectName;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private double goalAmount;
    @Column(nullable = false)
    private double currentAmount;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    @Column(nullable = false)
    private String status;
    @ManyToOne
    @JoinColumn(name = "users")
    private User projectManagerId;
    private Date createdAt;
    private Date updatedAt;

    public DonationProject(int projectId, String projectName, String description, double goalAmount, double currentAmount, Date startDate, Date endDate, String status, User projectManagerId, Date createdAt, Date updatedAt) {
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

    public User getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(User projectManagerId) {
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
