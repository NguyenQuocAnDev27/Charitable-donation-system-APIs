package com.example.DonationInUniversity.model;

public class AuthenticationResponse {
    private String jwt;

    // Constructor
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    // Getter
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}

