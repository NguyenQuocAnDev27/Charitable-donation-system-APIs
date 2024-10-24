package com.example.DonationInUniversity.model;

public class TransactionResponse {
    private String id;
    private String description;
    private String value;
    private String date;
    private String donorName;  // This stores the donor's account number (mã account)
    private String projectName;

    public TransactionResponse() {
    }

    public TransactionResponse(String id, String description, String value, String date, String donorName, String projectName) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.date = date;
        this.donorName = donorName;
        this.projectName = projectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getdonorName() {
        return donorName;
    }

    public void setdonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
