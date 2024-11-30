package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.service.api.UserService;
import com.example.DonationInUniversity.utils.JwtUtil;
//import com.example.DonationInUniversity.utils.Sha256PasswordEncoder;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${google.client.id}")
    private String GOOGLE_CLIENT_ID;

    private static final String GOOGLE_TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=";

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<MyCustomResponse<VerifiedUser>> registerUser(@RequestBody UserRegistrationRequest request) {
        logger.info("User registration requested: Full Name: {}, Email: {}", request.getFullName(), request.getEmail());

        // Step 1: Check if email already exists
        if (userService.isEmailExists(request.getEmail())) {
            logger.warn("Registration failed: Email already exists - {}", request.getEmail());
            return ResponseEntity.badRequest().body(new MyCustomResponse<>(400, "Email already exists", null));
        }

        // Step 2: Create new user
        VerifiedUser newUser = new VerifiedUser();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());

        // Hash the password using Sha256PasswordEncoder
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUser.setPhoneNumber(request.getPhoneNumber());

        VerifiedUser savedUser = userService.saveUser(newUser, "normal_user");

        logger.info("User registration successful: Email: {}", request.getEmail());
        return ResponseEntity.ok(new MyCustomResponse<>(200, "User registered successfully", savedUser));
    }


    // Register new admin user
    @PostMapping("/register/admin")
    public ResponseEntity<MyCustomResponse<VerifiedUser>> registerAdminUser(@RequestBody UserRegistrationRequest request) {
        logger.info("Admin registration requested: Full Name: {}, Email: {}", request.getFullName(), request.getEmail());

        VerifiedUser newUser = new VerifiedUser();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());

        // Hash the password using Sha256PasswordEncoder
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUser.setPhoneNumber(request.getPhoneNumber());

        VerifiedUser savedUser = userService.saveUser(newUser, "admin");

        logger.info("Admin registration successful: Email: {}", request.getEmail());
        return ResponseEntity.ok(new MyCustomResponse<>(200, "Admin registered successfully", savedUser));
    }

    // Dev: For testing password hash
    //logger.info("User authentication successful: Passwordhash: {}", passwordEncoder.encode(request.getPassword()));

    @PostMapping("/authenticate")
    public ResponseEntity<MyCustomResponse<?>> authenticateUser(@RequestBody AuthenticationRequest request) {
        logger.info("User authentication requested: Email: {}", request.getEmail());

        try {
            // Step 1: Load the user by email
            VerifiedUser user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Step 2: Compare the provided password with the stored hashed password
            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                logger.warn("Authentication failed: Invalid credentials for user {}", request.getEmail());
                throw new RuntimeException("Invalid credentials");
            }

            // Step 3: Authentication successful, generate both access and refresh tokens
            String accessToken = jwtUtil.generateToken(user.getEmail(), 3600);  // 1 hour (3600 seconds)
            String refreshToken = jwtUtil.generateToken(user.getEmail(), 604800);  // 7 days (604800 seconds)

            // Step 4: Save refresh token to db
            userService.saveRefreshToken(user.getUserId(), refreshToken);
            logger.info("User authentication successful: Email: {}", request.getEmail());

            // Return both tokens
            return ResponseEntity.ok(new MyCustomResponse<>(200, "Authentication successful", new AuthenticationResponse(accessToken, refreshToken)));

        } catch (Exception e) {
            logger.error("Authentication failed for user {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body(new MyCustomResponse<>(400, "Invalid credentials - " + e.getMessage(), null));
        }
    }

    @PostMapping("/authenticate/google")
    public ResponseEntity<MyCustomResponse<?>> authenticateWithGoogle(@RequestBody GoogleAuthRequest request) {
        try {

            // Step 1: Validate Google ID token by sending it to Google's API
            String googleEmail = validateGoogleToken(request.getIdToken());
            if (googleEmail == null) {
                throw new RuntimeException("Invalid Google ID token");
            }

            // Step 2: Load user by email (Google email)
            VerifiedUser user = userService.findByEmail(googleEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Step 3: Generate access and refresh tokens for the authenticated user
            String accessToken = jwtUtil.generateToken(user.getEmail(), 3600);  // 1 hour (3600 seconds)
            String refreshToken = jwtUtil.generateToken(user.getEmail(), 604800);  // 7 days (604800 seconds)

            // Step 4: Save refresh token to DB (optional)
            userService.saveRefreshToken(user.getUserId(), refreshToken);

            // Step 5: Return both tokens in the response
            return ResponseEntity.ok(new MyCustomResponse<>(200, "Authentication successful", new AuthGoogleResponse(
                    accessToken, refreshToken, googleEmail)));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MyCustomResponse<>(400, "Invalid credentials - " + e.getMessage(), null));
        }
    }

    // Utility method to validate Google ID token
    private String validateGoogleToken(String idToken) throws Exception {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                .build();

        // Verify the ID token
        GoogleIdToken token = verifier.verify(idToken);
        if (token != null) {
            GoogleIdToken.Payload payload = token.getPayload();
            String email = payload.getEmail();  // Extract the email from the token payload
            return email;
        } else {
            throw new Exception("Invalid ID token");
        }
    }

    // Get user information by email
    @GetMapping("/getInfoDetail")
    public ResponseEntity<MyCustomResponse<UserProfile>> getUserInfo(@RequestParam String email) {
        logger.info("User info requested: Email: {}", email);

        return userService.findByEmail(email)
                .map(user -> {
                    logger.info("User info retrieved successfully: Email: {}", email);
                    UserProfile userProfile = userService.convertToUserProfile(user); // Convert to UserProfile
                    return ResponseEntity.ok(new MyCustomResponse<>(200, "User found", userProfile));
                })
                .orElseGet(() -> {
                    logger.warn("User info not found: Email: {}", email);
                    return ResponseEntity.badRequest().body(new MyCustomResponse<>(400, "User not found", null));
                });
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<MyCustomResponse<?>> refreshAccessToken(@RequestBody TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        try {
            // Step 1: Validate refresh token in the database
            if (!userService.isRefreshTokenValid(refreshToken)) {
                logger.warn("Invalid or expired refresh token");
                throw new RuntimeException("Invalid or expired refresh token");
            }

            // Step 2: Get the user associated with the refresh token
            VerifiedUser user = userService.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Step 3: Generate new access token
            String newAccessToken = jwtUtil.generateToken(user.getEmail(), 3600);  // 1 hour

            logger.info("Access token refreshed for user: {}", user.getEmail());

            return ResponseEntity.ok(new MyCustomResponse<>(200, "Access token refreshed successfully", new AuthenticationResponse(newAccessToken, refreshToken)));

        } catch (Exception e) {
            logger.error("Failed to refresh access token: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MyCustomResponse<>(400, "Failed to refresh token - " + e.getMessage(), null));
        }
    }

}
