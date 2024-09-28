package com.example.DonationInUniversity.model;
import java.util.Date;

public class Notification {
    private int notificationId;
    private int userId;
    private String message;
    private boolean isRead;
    private Date sentAt;

    public Notification(int notificationId, int userId, String message, boolean isRead, Date sentAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
        this.sentAt = sentAt;
    }

    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
}
