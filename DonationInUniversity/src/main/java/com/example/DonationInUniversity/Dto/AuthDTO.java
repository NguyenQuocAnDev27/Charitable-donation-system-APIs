package com.example.DonationInUniversity.Dto;

public class AuthDTO {
    private String id;
    private String fullName;
    private String email;
    private String jwt_token;
    private String phoneNumber;
    private int roleId;

    public AuthDTO() {
    }

    public AuthDTO(String id, String fullName, String email, String jwt_token, String phoneNumber, int roleId) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.jwt_token = jwt_token;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getJwt_token() {
        return jwt_token;
    }

    public void setJwt_token(String jwt_token) {
        this.jwt_token = jwt_token;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
