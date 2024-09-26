package com.example.DonationInUniversity.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgot_password")
    public String forgot_password() {
        return "forgot-password";
    }
}
