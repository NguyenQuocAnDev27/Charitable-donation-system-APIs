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

    public boolean isTokenValid(String token, String username) {
        try {
            logger.info("Validating token for username: {}", username);

            boolean isUsernameMatch = isUsernameMatching(token, username);
            boolean isTokenNotExpired = !isTokenExpired(token);

            logger.info("Username match: {}, Token expired: {}", isUsernameMatch, !isTokenNotExpired);

            return (isUsernameMatch && isTokenNotExpired);
        } catch (Exception e) {
            logger.error("Error while validating token: {}", e.getMessage());
            return false;
        }
    }

    // Method to check if the username matches the one in the token
    public boolean isUsernameMatching(String token, String username) {
        try {
            Claims claims = parseClaims(token);
            String extractedUsername = claims.getSubject();
            logger.info("Extracted username from token: {}", extractedUsername);
            return extractedUsername.equals(username);
        } catch (Exception e) {
            logger.error("Error while extracting username from token: {}", e.getMessage());
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
