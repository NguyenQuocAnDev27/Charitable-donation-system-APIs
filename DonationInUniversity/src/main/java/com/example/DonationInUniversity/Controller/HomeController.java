package com.example.DonationInUniversity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homepage() {
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "auth-login-basic";
    }
    @GetMapping("/register")
    public String register() {
        return "auth-register-basic";
    }
    @GetMapping("/forgot_password")
    public String forgot_password() {
        return "auth-forgot-password-basic";
    }
    @GetMapping("/DonationProject")
    public String DonationProject() {
        return "DonationProject";
    }
}
