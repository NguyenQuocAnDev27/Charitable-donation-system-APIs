package com.example.DonationInUniversity.security;

import com.example.DonationInUniversity.utils.Sha256PasswordEncoder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final Sha256PasswordEncoder sha256PasswordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtRequestFilter(@Lazy Sha256PasswordEncoder sha256PasswordEncoder, @Lazy UserDetailsService userDetailsService) {
        this.sha256PasswordEncoder = sha256PasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        logger.info("Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            logger.info("Extracted JWT: " + jwt);

            try {
                username = sha256PasswordEncoder.extractUsername(jwt);
                logger.info("Extracted Username from JWT: " + username);
            } catch (Exception e) {
                logger.error("JWT error: " + e.getMessage(), e);
            }
        } else {
            logger.warn("Authorization header missing or does not start with Bearer");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("Security context is null. Trying to authenticate user: " + username);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            logger.info("UserDetails loaded for user: " + userDetails.getUsername());

            if (sha256PasswordEncoder.isTokenValid(jwt, userDetails.getUsername())) {
                logger.info("JWT is valid for user: " + userDetails.getUsername());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("User authenticated successfully: " + username);
            } else {
                logger.warn("JWT is not valid for user: " + username);
            }
        } else if (username != null) {
            logger.info("User is already authenticated: " + username);
        }

        chain.doFilter(request, response);
    }

}
