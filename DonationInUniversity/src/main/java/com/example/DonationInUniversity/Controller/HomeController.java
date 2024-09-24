package com.example.DonationInUniversity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homepage() {
        return "index";
    }
    @GetMapping("/auth-login-basic")
    public String login() {
        return "auth-login-basic";
    }
    @GetMapping("/DonationProject")
    public String DonationProject() {
        return "DonationProject";
    }
}
