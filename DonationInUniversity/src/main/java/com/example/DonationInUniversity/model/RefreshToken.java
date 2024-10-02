package com.example.DonationInUniversity.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Refresh_Tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer tokenId;  // Mapping cho `token_id`

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private VerifiedUser user;  // Mapping cho `user_id`

    @Column(name = "token", nullable = false, unique = true)
    private String token;  // Mapping cho `token`

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;  // Mapping cho `expires_at`

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;  // Mapping cho `created_at` với giá trị mặc định là thời gian hiện tại

    // Getters và Setters
    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public VerifiedUser getUser() {
        return user;
    }

    public void setUser(VerifiedUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

