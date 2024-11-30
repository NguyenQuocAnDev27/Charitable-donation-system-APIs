package com.example.DonationInUniversity.model;

public class GoogleAuthRequest {
    private String idToken;  // The ID token received from Google Sign-In

    // Constructor
    public GoogleAuthRequest() {}

    // Getter and Setter for idToken
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
