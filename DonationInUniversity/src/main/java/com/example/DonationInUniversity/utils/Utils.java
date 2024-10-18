package com.example.DonationInUniversity.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {
    public static String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256 hashing algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Hash the password
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert the hashed bytes to Base64 string for storage
            return Base64.getEncoder().encodeToString(hashedBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred while hashing the password", e);
        }
    }

    public static String formatAddInfo (String user_id, String project_id, String message) {
        return "SGUC" + project_id + "USR" + user_id + "M" + message;
    }

    public static Integer extractProjectId(String description) {
        try {
            // Format: "SGUC{mã chiến dịch}USR{mã account}M{lời nhắn}"
            String[] parts = description.split("SGUC|USR");
            if (parts.length > 1) {
                return Integer.parseInt(parts[1].trim());  // Extract the mã chiến dịch
            }
        } catch (Exception e) {
            System.err.println("Unable to extract project ID from description: " + description);
        }
        return null;
    }

    public static String extractUserIdFromDescription(String description) {
        if (description != null && description.contains("USR")) {
            // The account number starts after "USR"
            int startIndex = description.indexOf("USR") + 3;  // Add 3 to move past "USR"

            // The account number ends before the letter "M"
            int endIndex = description.indexOf("M", startIndex);

            // Ensure both "USR" and "M" are present and in the correct order
            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                // Extract and return the account number substring
                return description.substring(startIndex, endIndex).trim();
            }
        }
        return null;  // Return null if "USR" or "M" are not found or the format is invalid
    }
}
