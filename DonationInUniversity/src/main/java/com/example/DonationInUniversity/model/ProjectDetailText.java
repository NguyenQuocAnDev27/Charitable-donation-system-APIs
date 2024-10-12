package com.example.DonationInUniversity.model;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ProjectDetailText")
public class ProjectDetailText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private DonationProject donationProject;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "IsDelete", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDelete = false;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdAt;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public DonationProject getDonationProject() {
        return donationProject;
    }

    public void setDonationProject(DonationProject donationProject) {
        this.donationProject = donationProject;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}

