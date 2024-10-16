package com.example.DonationInUniversity.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Projectdetailimage")
public class ProjectDetailImageAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private DonationProject donationProject;


    @Column(name = "path_image", nullable = false)
    private String pathImage;

    @Column(name = "is_delete", nullable = false)
    private int isDelete;

    @Column(name = "display_order", nullable = false)
    private Integer orderNo;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DonationProject getDonationProject() {
        return donationProject;
    }

    public void setDonationProject(DonationProject donationProject) {
        this.donationProject = donationProject;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
