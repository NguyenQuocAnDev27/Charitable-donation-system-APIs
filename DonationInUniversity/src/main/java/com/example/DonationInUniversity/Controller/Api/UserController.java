package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.security.JwtTokenUtil;
import com.example.DonationInUniversity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<MyCustomResponse<VerifiedUser>> registerUser(@RequestBody UserRegistrationRequest request) {
        VerifiedUser newUser = new VerifiedUser();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPasswordHash(request.getPassword()); // Password will be hashed in the service
        newUser.setPhoneNumber(request.getPhoneNumber());

        // Save the user with a default role
        VerifiedUser savedUser = userService.saveUser(newUser, "normal_user");

        // Return response with registered user data
        return ResponseEntity.ok(new MyCustomResponse<>(200, "User registered successfully", savedUser));
    }

    // Register new user
    @PostMapping("/register/admin")
    public ResponseEntity<MyCustomResponse<VerifiedUser>> registerAdminUser(@RequestBody UserRegistrationRequest request) {
        VerifiedUser newUser = new VerifiedUser();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPasswordHash(request.getPassword()); // Password will be hashed in the service
        newUser.setPhoneNumber(request.getPhoneNumber());

        // Save the user with a default role
        VerifiedUser savedUser = userService.saveUser(newUser, "admin");

        // Return response with registered user data
        return ResponseEntity.ok(new MyCustomResponse<>(200, "User registered successfully", savedUser));
    }


    // Authenticate user and return JWT
    @PostMapping("/authenticate")
    public ResponseEntity<MyCustomResponse<?>> authenticateUser(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MyCustomResponse<>(400, "Invalid credentials", null));
        }

        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new MyCustomResponse<>(200, "Authentication successful", new AuthenticationResponse(jwt)));
    }


    @GetMapping("/getInfoDetail")
    public ResponseEntity<MyCustomResponse<VerifiedUser>> getUserInfo(@RequestParam String email) {
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(new MyCustomResponse<VerifiedUser>(200, "User found", user)))
                .orElseGet(() -> ResponseEntity.badRequest().body(new MyCustomResponse<VerifiedUser>(400, "User not found", null)));
    }

}
