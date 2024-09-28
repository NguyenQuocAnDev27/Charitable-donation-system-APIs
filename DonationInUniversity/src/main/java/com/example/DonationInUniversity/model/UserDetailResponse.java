package com.example.DonationInUniversity.model;

public class UserDetailResponse {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String role;

    // Constructor to map fields from VerifiedUser
    public UserDetailResponse(VerifiedUser user) {
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole().getRoleName(); // Assuming Role has a getRoleName() method
    }

    // Getters and Setters
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

