package com.example.DonationInUniversity.model;

public class RequestPaymentResponse {
    private String projectId;
    private String projectName;
    private String qrContent;

    // Constructors, Getters and Setters
    public RequestPaymentResponse(String projectId, String projectName, String qrContent) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.qrContent = qrContent;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getQrContent() {
        return qrContent;
    }

    public void setQrContent(String qrContent) {
        this.qrContent = qrContent;
    }
}
