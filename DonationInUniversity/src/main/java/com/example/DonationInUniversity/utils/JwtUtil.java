package com.example.DonationInUniversity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
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
            logger.debug("Generating token for subject: {}", subject);

            SecretKey key = getSecretKey();
            Date expirationDate = new Date(System.currentTimeMillis() + (exp_time_seconds * 1000L));
            String token = Jwts.builder()
                    .subject(subject)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(expirationDate)
                    .signWith(key, Jwts.SIG.HS256)
                    .compact();

            logger.info("Token generated successfully for subject: {}. Expiration time: {}", subject, expirationDate);
            return token;
        } catch (Exception e) {
            logger.error("Error while generating token for subject: {}: {}", subject, e.getMessage());
            throw new RuntimeException("Error while generating token", e);
        }
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        try {
            logger.info("Checking if token is expired: {}", token);
            Claims claims = parseClaims(token);
            Date expiration = claims.getExpiration();

            // Convert both times to Instant
            Instant expirationInstant = expiration.toInstant();
            Instant nowInstant = Instant.now();

            // Compare the instants
            boolean isExpired = expirationInstant.isBefore(nowInstant);

            logger.info("\nToken expiration status: {}. Expiration time: {} - Now: {}", isExpired, expiration, nowInstant);
            return isExpired;
        } catch (Exception e) {
            logger.error("Error while checking token expiration for token: {}: {}", token, e.getMessage());
            throw new RuntimeException("Error while checking token expiration", e);
        }
    }


    public boolean isTokenValid(String token, String username) {
        try {
            logger.info("Validating token for username: {}", username);
            boolean isUsernameMatch = isUsernameMatching(token, username);
            boolean isTokenNotExpired = !isTokenExpired(token);

            logger.info("Token validation result - Username match: {}, Token expired: {}", isUsernameMatch, !isTokenNotExpired);
            return (isUsernameMatch && isTokenNotExpired);
        } catch (Exception e) {
            logger.error("Error while validating token for username: {}: {}", username, e.getMessage());
            return false;
        }
    }

    // Method to check if the username matches the one in the token
    public boolean isUsernameMatching(String token, String username) {
        try {
            logger.info("Checking if token username matches provided username: {}", username);
            Claims claims = parseClaims(token);
            String extractedUsername = claims.getSubject();
            logger.info("Extracted username from token: {}", extractedUsername);
            return extractedUsername.equals(username);
        } catch (Exception e) {
            logger.error("Error while extracting username from token for username: {}: {}", username, e.getMessage());
            return false;
        }
    }

    // Extract username from token
    public String extractUsername(String token) {
        try {
            logger.info("Extracting username from token: {}", token);
            Claims claims = parseClaims(token);
            String username = claims.getSubject();
            logger.info("Username extracted from token: {}", username);
            return username;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while extracting username from token: {}: {}", token, e.getMessage());
            throw new RuntimeException("Error while extracting username", e);
        }
    }

    // Parse claims from token
    private Claims parseClaims(String token) {
        try {
            logger.info("Parsing claims from token: {}", token);
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            logger.info("Claims parsed successfully for token: {}", token);
            return claims;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while parsing claims from token: {}: {}", token, e.getMessage());
            throw new RuntimeException("Error while parsing claims", e);
        }
    }

    // Get secret key
    private SecretKey getSecretKey() {
        try {
            logger.info("Decoding secret key.");
            byte[] decodedKey = Base64.getDecoder().decode(secretKey);
            return new SecretKeySpec(decodedKey, 0, decodedKey.length, HMAC_SHA256);
        } catch (Exception e) {
            logger.error("Error while decoding secret key: {}", e.getMessage());
            throw new RuntimeException("Error while decoding secret key", e);
        }
    }

    public Date getExpirationDateFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.getExpiration();
    }

}

