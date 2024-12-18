package com.example.DonationInUniversity.security;

import com.example.DonationInUniversity.constants.ResponseConstants;
import com.example.DonationInUniversity.model.MyCustomResponse;
import com.example.DonationInUniversity.service.api.EndpointService;
import com.example.DonationInUniversity.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Component
public class ApiRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestFilter.class);

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final EndpointService endpointService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    public ApiRequestFilter(@Lazy JwtUtil jwtUtil,
                            @Qualifier("userService") @Lazy UserDetailsService userDetailsService,
                            @Lazy EndpointService endpointService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.endpointService = endpointService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        int requestPort = request.getLocalPort();

        if ((requestPort + "").equals(serverPort)) {

            final String authorizationHeader = request.getHeader("Authorization");
            String username = null;
            String jwt = null;

            // Log the requested endpoint
            String requestURI = request.getRequestURI();
            logger.info("Requested Endpoint: {}", requestURI);
            logger.info("Authorization Header: {}", authorizationHeader);

            // Extracting username
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                logger.info("Extracted Access token: {}", jwt);

                try {
                    username = jwtUtil.extractUsername(jwt);
                    logger.info("Extracted Username from Access token: {}", username);
                } catch (ExpiredJwtException e) {
                    respondWithCustomError(
                            response,
                            ResponseConstants.EXPIRED_TOKEN.CODE,
                            ResponseConstants.EXPIRED_TOKEN.MESSAGE);
                    return;
                } catch (Exception e) {
                    logger.error("Access token error: {}", e.getMessage(), e);
                }
            } else {
                logger.warn("Authorization header missing or does not start with Bearer");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.info("Security context is null. Trying to authenticate user: {}", username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                logger.info("UserDetails loaded for user: {}", userDetails.getUsername());


                if (jwtUtil.isUsernameMatching(jwt, userDetails.getUsername())) {
                    logger.info("Access token is valid for user: {}", userDetails.getUsername());

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.info("User authenticated successfully: {}", username);
                } else {
                    logger.warn("Access token is not valid for user: {}", username);
                }
            } else if (username != null) {
                logger.info("User is already authenticated: {}", username);
            }

            // Check if the endpoint exists
//          if (!isValidEndpoint(requestURI)) {
//              respondWithCustomError(response, 500, "No API endpoint named like that");
//              return;
//          }
            chain.doFilter(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }


    // Custom method to respond with an error
    private void respondWithCustomError(HttpServletResponse response, int status, String message) throws IOException {
        logger.warn("<<<>>> Return the custom reponse");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);

        MyCustomResponse<Object> customResponse = new MyCustomResponse<>(status, message, null);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(new ObjectMapper().writeValueAsString(customResponse));
            writer.flush();
        }
    }


    private boolean isValidEndpoint(String requestURI) {
        List<String> validEndpoints = endpointService.getAllEndpoints();

        // *** Note  Dev: For testing
        // logger.info("List of Valid APIs: " + validEndpoints);
        // logger.info("Requested API: " + requestURI);

        // Normalize the requested URI (optional, depending on your needs)
        String normalizedRequestURI = requestURI.trim();

        // Check if the requested URI exists in the valid endpoints
        for (String endpoint : validEndpoints) {
            // Extract the URI from the endpoint string (assuming they are in a specific format)
            String validUri = extractUriFromEndpoint(endpoint);
            // Dev: For testing
            // logger.info("validUri: " + validUri + " compare with " + normalizedRequestURI);
            if (validUri.equals(normalizedRequestURI)) {
                return true;
            }
        }

        return false;
    }

    // Helper method to extract the URI from the endpoint representation
    private String extractUriFromEndpoint(String endpoint) {
        // Assuming the endpoint is formatted like {GET [/api/projects]}, you can parse it accordingly
        String[] parts = endpoint.split(" "); // Split by space to separate method and URI
        if (parts.length > 1) {
            // Extract the URI from the second part, removing brackets
            return parts[1].replaceAll("[\\[\\]}]", ""); // Remove "[" "]" "}"
        }
        return "";
    }

}
