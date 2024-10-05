package com.example.DonationInUniversity.constants;

public class ResponseConstants {

    // Inner static class representing a response constant with code and message
    public static class Status {
        public final int CODE;
        public final String MESSAGE;

        public Status(int code, String message) {
            this.CODE = code;
            this.MESSAGE = message;
        }
    }

    // Custom status codes (600 and above)
    public static final Status EXPIRED_TOKEN = new Status(600, "Expired Access Token");

    // Private constructor to prevent instantiation
    private ResponseConstants() {
        throw new IllegalStateException("Constants class");
    }
}
