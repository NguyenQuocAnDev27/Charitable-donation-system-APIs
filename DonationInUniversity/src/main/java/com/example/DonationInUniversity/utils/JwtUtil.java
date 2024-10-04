package com.example.DonationInUniversity.utils;

import com.example.DonationInUniversity.controller.api.UserController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secretKey;

    // Generate JWT token
    public String generateToken(String subject, int exp_time_seconds) {
        try {
            SecretKey key = getSecretKey();
            Date expirationDate = new Date(System.currentTimeMillis() + (exp_time_seconds * 1000L));
            String token = Jwts.builder()
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expirationDate)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            logger.info("Token generated. Expiration time: {}", expirationDate);
            return token;
        } catch (Exception e) {
            logger.error("Error while generating token: {}", e.getMessage());
            throw new RuntimeException("Error while generating token", e);
        }
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseClaims(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            logger.error("Error while checking token expiration: {}", e.getMessage());
            throw new RuntimeException("Error while checking token expiration", e);
        }
    }

    // Validate token
    public boolean isTokenValid(String token, String username) {
        try {
            logger.info("Validating token for username: {}", username);

            Claims claims = parseClaims(token);
            String extractedUsername = claims.getSubject();
            logger.info("Extracted username from token: {}", extractedUsername);

            boolean isUsernameMatch = extractedUsername.equals(username);
            boolean isTokenNotExpired = !isTokenExpired(token);

            logger.info("Username match: {}, Token expired: {}", isUsernameMatch, !isTokenNotExpired);

            return (isUsernameMatch && isTokenNotExpired);
        } catch (Exception e) {
            logger.error("Error while validating token: {}", e.getMessage());
            return false;
        }
    }

    // Extract username from token
    public String extractUsername(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            logger.error("Error while extracting username: {}", e.getMessage());
            throw new RuntimeException("Error while extracting username", e);
        }
    }

    // Parse claims from token
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get secret key
    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, HMAC_SHA256);
    }
}
//public class Sha256PasswordEncoder implements PasswordEncoder {
//    private static final String HMAC_SHA256 = "HmacSHA256";
//    private static final Logger logger = LoggerFactory.getLogger(Sha256PasswordEncoder.class);
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Override
//    public String encode(CharSequence rawPassword) {
//        try {
//            // Step 1: Use the secret key directly for HMAC-SHA-256
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256);
//
//            // Step 2: Hash the password using HMAC-SHA-256
//            javax.crypto.Mac sha256HMAC = javax.crypto.Mac.getInstance(HMAC_SHA256);
//            sha256HMAC.init(secretKeySpec);
//            byte[] hashedBytes = sha256HMAC.doFinal(rawPassword.toString().getBytes());
//            return bytesToHex(hashedBytes);
//        } catch (Exception e) {
//            throw new RuntimeException("Error while hashing password", e);
//        }
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        String hashedPassword = encode(rawPassword);
//        return hashedPassword.equals(encodedPassword);
//    }
//
//    // Utility method to convert byte array to hexadecimal string
//    private String bytesToHex(byte[] bytes) {
//        StringBuilder hexString = new StringBuilder();
//        for (byte b : bytes) {
//            String hex = Integer.toHexString(0xff & b);
//            if (hex.length() == 1) hexString.append('0');
//            hexString.append(hex);
//        }
//        return hexString.toString();
//    }
//
//    // Method to generate JWT token using the secret key
//    public String generateToken(String subject, int exp_time) {
//        try {
//            Date expirationDate = new Date(System.currentTimeMillis() + (exp_time * 1000L));
//            String token = Jwts.builder()
//                    .subject(subject)
//                    .issuedAt(new Date(System.currentTimeMillis()))
//                    .expiration(expirationDate)
//                    .signWith(getSecretKey(), Jwts.SIG.HS256)
//                    .compact();
//
//            // Log the expiration time of the token
//            logger.info("Token generated. Expiration time: {}", expirationDate);
//            return token;
//        } catch (Exception e) {
//            throw new RuntimeException("Error while generating token", e);
//        }
//    }
//
//    // Method to check if token is expired
//    public boolean isTokenExpired(String token) {
//        try {
//            Date expiration = Jwts.parser()
//                    .verifyWith(getSecretKey())
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload()
//                    .getExpiration();
//            return expiration.before(new Date());
//        } catch (Exception e) {
//            throw new RuntimeException("Error while checking token expiration", e);
//        }
//    }
//
//    // Method to validate the token by username and expiration
//    public boolean isTokenValid(String token, String username) {
//        try {
//            logger.info("Validating token for username: {}", username);
//
//            // Parse the token and extract the subject (username)
//            final String extractedUsername = Jwts.parser()
//                    .verifyWith(getSecretKey())  // Validate with the secret key
//                    .build()
//                    .parseSignedClaims(token)    // Parse and validate the token
//                    .getPayload()                // Get the claims (payload)
//                    .getSubject();               // Extract the subject (username)
//
//            logger.info("Extracted username from token: {}", extractedUsername);
//
//            boolean isUsernameMatch = extractedUsername.equals(username);
//            boolean isTokenNotExpired = !isTokenExpired(token);
//
//            logger.info("Username match: {}, Token expired: {}", isUsernameMatch, !isTokenNotExpired);
//
//            // Check if the extracted username matches and the token is not expired
//            return (isUsernameMatch && isTokenNotExpired);
//        } catch (Exception e) {
//            logger.error("Error while validating token: {}", e.getMessage());
//            throw new RuntimeException("Error while validating token", e);
//        }
//    }
//
//
//    public SecretKey getSecretKey() {
//        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
//        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
//    }
//
//    public String extractUsername(String token) {
//        return Jwts.parser()
//                .verifyWith(getSecretKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload()
//                .getSubject();
//    }
//}
