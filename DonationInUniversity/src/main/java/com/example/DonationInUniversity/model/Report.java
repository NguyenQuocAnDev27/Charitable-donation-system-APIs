package com.example.DonationInUniversity.model;
import java.util.Date;

public class Report {
    private int reportId;
    private int projectId;
    private double totalDonations;
    private String impact;
    private Date createdAt;

    public Report(int reportId, int projectId, double totalDonations, String impact, Date createdAt) {
        this.reportId = reportId;
        this.projectId = projectId;
        this.totalDonations = totalDonations;
        this.impact = impact;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public double getTotalDonations() {
        return totalDonations;
    }

    public void setTotalDonations(double totalDonations) {
        this.totalDonations = totalDonations;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
