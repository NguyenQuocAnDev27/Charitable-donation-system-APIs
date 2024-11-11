package com.example.DonationInUniversity.model;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;

@Entity
@Table(name = "transfer_application")
public class TransferApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private DonationProject projectId;

    @Column(name = "document_path")
    private String documentPath;

    @Column(name = "bill_path")
    private String billPath;

    @Column(name = "amount", precision = 19, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
    @Transient
    private String projectName;
    // Constructors, getters, and setters
    @Transient
    private MultipartFile documentFile;
    public TransferApplication() {
    }

    public TransferApplication(DonationProject projectId, String documentPath, String billPath, BigDecimal amount, String status, User userId) {
        this.projectId = projectId;
        this.documentPath = documentPath;
        this.billPath = billPath;
        this.amount = amount;
        this.status = status;
        this.userId = userId;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DonationProject getProjectId() {
        return projectId;
    }

    public void setProjectId(DonationProject projectId) {
        this.projectId = projectId;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getBillPath() {
        return billPath;
    }

    public void setBillPath(String billPath) {
        this.billPath = billPath;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public MultipartFile getDocumentFile() {
        return documentFile;
    }
    public void setDocumentFile(MultipartFile documentFile) {
        this.documentFile = documentFile;
    }
}

