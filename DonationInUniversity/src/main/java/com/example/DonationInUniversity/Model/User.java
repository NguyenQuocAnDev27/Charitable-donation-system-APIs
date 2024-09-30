package com.example.DonationInUniversity.Model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "projectManagerId")
    private Set<DonationProject> project_manager;

    public User(int userId, String fullName, String email, String phoneNumber, int role, Object o, Object object) {}

    public User(String email, Set<DonationProject> project_manager, Date createdAt, Date updatedAt, Role role, String phoneNumber, String passwordHash, String fullName, int userId) {
        this.email = email;
        this.project_manager = project_manager;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.userId = userId;
    }

    public User() {

    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public Set<DonationProject> getProject_manager() {
        return project_manager;
    }

    public void setProject_manager(Set<DonationProject> project_manager) {
        this.project_manager = project_manager;
    }
}

