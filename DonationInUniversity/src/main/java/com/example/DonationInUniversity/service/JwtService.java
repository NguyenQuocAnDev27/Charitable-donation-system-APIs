//package com.example.DonationInUniversity.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.security.Key;
//import java.util.Date;
//
//@Service
//public class JwtService {
//    @Value("${JWT_SECRET}")
//    private String secret;
//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//    private final SecretKey SECRET_KEY = (SecretKey) getSigningKey();
//    private final UserDetailsService userDetailsService;
//
//    public JwtService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    // Generate JWT token for a given username
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .subject(username)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
//                .signWith(SECRET_KEY)
//                .compact();
//    }
//
//    // Validate JWT token
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().verifyWith(SECRET_KEY)
//                    .build()
//                    .parseSignedClaims(token)
//                    .getPayload();
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // Extract username from JWT token
//    public String extractUsername(String token) {
//        Claims claims = Jwts.parser().verifyWith(SECRET_KEY)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//        return claims.getSubject();
//    }
//
//    // Create authentication object for Spring Security
//    public Authentication getAuthentication(String token) {
//        String username = extractUsername(token);
//        var userDetails = userDetailsService.loadUserByUsername(username);
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }
//}
