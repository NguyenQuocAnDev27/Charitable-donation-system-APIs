package com.example.DonationInUniversity.model;
import java.util.Date;

public class TransactionPayMethod {
    private int payMethodId;
    private String payMethodName;
    private Date createdAt;

    public TransactionPayMethod(int payMethodId, String payMethodName, Date createdAt) {
        this.payMethodId = payMethodId;
        this.payMethodName = payMethodName;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(int payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getPayMethodName() {
        return payMethodName;
    }

    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
