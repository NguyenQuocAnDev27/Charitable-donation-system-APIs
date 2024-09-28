package com.example.DonationInUniversity.model;
import java.util.Date;

public class Donation {
    private int donationId;
    private String transactionId;
    private double amount;
    private Date donationDate;
    private String donorName;
    private int projectId;
    private String status;
    private int payMethodId;
    private Date createdAt;

    public Donation(int donationId, String transactionId, double amount, Date donationDate, String donorName, int projectId, String status, int payMethodId, Date createdAt) {
        this.donationId = donationId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.donationDate = donationDate;
        this.donorName = donorName;
        this.projectId = projectId;
        this.status = status;
        this.payMethodId = payMethodId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(int payMethodId) {
        this.payMethodId = payMethodId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
