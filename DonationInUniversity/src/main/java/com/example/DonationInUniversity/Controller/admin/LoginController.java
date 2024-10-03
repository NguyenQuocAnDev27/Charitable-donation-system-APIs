package com.example.DonationInUniversity.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class LoginController {
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgot_password")
    public String forgot_password() {
        return "forgot-password";
    }
}
