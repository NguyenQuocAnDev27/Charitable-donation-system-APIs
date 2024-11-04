package com.example.DonationInUniversity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_detail_text")
public class ProjectDetailText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_delete", nullable = false)
    private Integer isDelete;

    @Column(name = "display_order", nullable = false)
    private Integer display_order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private DonationProject project;

    // Getters and setters
    public ProjectDetailText() {}

    public Integer getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(Integer display_order) {
        this.display_order = display_order;
    }

    public DonationProject getProject() {
        return project;
    }

    public void setProject(DonationProject project) {
        this.project = project;
    }
}

