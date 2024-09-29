package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.security.JwtTokenUtil;
import com.example.DonationInUniversity.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<MyCustomResponse<VerifiedUser>> registerUser(@RequestBody UserRegistrationRequest request) {
        logger.info("User registration requested: Full Name: {}, Email: {}", request.getFullName(), request.getEmail());

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

    // Authenticate user and return JWT
    @PostMapping("/authenticate")
    public ResponseEntity<MyCustomResponse<?>> authenticateUser(@RequestBody AuthenticationRequest request) {
        logger.info("User authentication requested: Email: {}", request.getEmail());

        try {
            // Step 1: Hash the provided password
            String providedPasswordHash = passwordEncoder.encode(request.getPassword());
            logger.warn("The password hash: {}", providedPasswordHash);

            // Step 2: Load the user by email (this should get the stored password hash from the database)
            VerifiedUser user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Step 3: Compare the hashed password with the stored password
            if (!providedPasswordHash.equals(user.getPasswordHash())) {
                logger.warn("Authentication failed: Invalid credentials for user {}", request.getEmail());
                throw new RuntimeException("Invalid credentials");
            }

            // Step 4: Authentication successful, generate JWT token
            String jwt = jwtTokenUtil.generateToken(user.getEmail());
            logger.info("User authentication successful: Email: {}", request.getEmail());

            return ResponseEntity.ok(new MyCustomResponse<>(200, "Authentication successful", new AuthenticationResponse(jwt)));

        } catch (Exception e) {
            logger.error("Authentication failed for user {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body(new MyCustomResponse<>(400, "Invalid credentials - " + e.getMessage(), null));
        }
    }

    // Get user information by email
    @GetMapping("/getInfoDetail")
    public ResponseEntity<MyCustomResponse<VerifiedUser>> getUserInfo(@RequestParam String email) {
        logger.info("User info requested: Email: {}", email);

        return userService.findByEmail(email)
                .map(user -> {
                    logger.info("User info retrieved successfully: Email: {}", email);
                    return ResponseEntity.ok(new MyCustomResponse<>(200, "User found", user));
                })
                .orElseGet(() -> {
                    logger.warn("User info not found: Email: {}", email);
                    return ResponseEntity.badRequest().body(new MyCustomResponse<>(400, "User not found", null));
                });
    }
}
