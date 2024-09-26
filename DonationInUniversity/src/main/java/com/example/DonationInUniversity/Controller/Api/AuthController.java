package com.example.DonationInUniversity.Controller.Api;

import com.example.DonationInUniversity.Dto.AuthDTO;

import com.example.DonationInUniversity.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public AuthDTO login(@RequestParam(name = "email") String email,
                         @RequestParam(name = "password") String password) {

        // Authenticate the user and get the AuthDTO
        AuthDTO authDTO = authService.login(email, password);

        // Return the AuthDTO if successful, or handle failure
        if (authDTO != null) {
            return authDTO;
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
