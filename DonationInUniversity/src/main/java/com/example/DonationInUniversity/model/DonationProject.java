package com.example.DonationInUniversity.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "donation_projects")
public class DonationProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @Column(nullable = false)
    private String projectName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal goalAmount;

    @Column(nullable = false)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String status = "pending";

    @ManyToOne
    @JoinColumn(name = "project_manager_id", referencedColumnName = "userId")
    private User projectManager;
    @Column(name = "is_deleted", nullable = false)
    private int isDeleted;
    @Transient
    private boolean hasAcceptTransfer;
    @Transient
    private boolean checkTransferRequest;
    @OneToMany(mappedBy = "project")
    private List<ProjectDetailText> projectDetailTexts;

    @OneToMany(mappedBy = "project")
    private List<ProjectDetailImage> projectDetailImages;
    // Getters and setters

    public List<ProjectDetailText> getProjectDetailTexts() {
        return projectDetailTexts;
    }

    public List<ProjectDetailImage> getProjectDetailImages() {
        return projectDetailImages;
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

    public User getProjectManager() {
        return projectManager;
    }
    public UserProfile getProjectManager2() {
        return new UserProfile(
                projectManager.getUserId(),
                projectManager.getFullName(),
                projectManager.getEmail(),
                projectManager.getPhoneNumber(),
                projectManager.getRole().getRoleName(),
                projectManager.getCreatedAt(),
                projectManager.getUpdatedAt(),
                projectManager.getIsDeleted() == 1
        );
    }

    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isHasAcceptTransfer() {
        return hasAcceptTransfer;
    }
    public void setHasAcceptTransfer(boolean hasAcceptTransfer) {
        this.hasAcceptTransfer = hasAcceptTransfer;
    }

    public boolean isCheckTransferRequest() {
        return checkTransferRequest;
    }
    public void setCheckTransferRequest(boolean checkTransferRequest) {
        this.checkTransferRequest = checkTransferRequest;
    }
}