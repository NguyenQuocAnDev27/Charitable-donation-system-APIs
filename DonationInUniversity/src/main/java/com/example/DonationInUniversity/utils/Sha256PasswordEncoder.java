package com.example.DonationInUniversity.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Date;

@Component
public class Sha256PasswordEncoder implements PasswordEncoder {
    private static final String HMAC_SHA256 = "HmacSHA256";

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            // Step 1: Hash the original jwt.secret using SHA-256
            byte[] hashedSecretKey = hashSecretKey(secretKey);

            // Step 2: Use the hashed key for HMAC-SHA-256
            Mac sha256HMAC = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(hashedSecretKey, HMAC_SHA256);
            sha256HMAC.init(secretKeySpec);

            // Step 3: Hash the password using HMAC-SHA-256
            byte[] hashedBytes = sha256HMAC.doFinal(rawPassword.toString().getBytes());
            return bytesToHex(hashedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = encode(rawPassword);
        return hashedPassword.equals(encodedPassword);
    }

    // Utility method to hash the secret key using SHA-256
    private byte[] hashSecretKey(String secretKey) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(secretKey.getBytes());
    }

    // Utility method to convert byte array to hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Method to generate JWT token using the hashed key
    public String generateToken(String subject) {
        try {
            // Step 1: Hash the original jwt.secret
            byte[] hashedSecretKey = hashSecretKey(secretKey);

            // Step 2: Use the hashed key for JWT signing
            return Jwts.builder()
                    .subject(subject)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                    .signWith(new SecretKeySpec(hashedSecretKey, HMAC_SHA256), Jwts.SIG.HS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }
}
