package com.example.DonationInUniversity.Utils;


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
}
