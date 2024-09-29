package com.example.DonationInUniversity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    //    @GetMapping("/DonationProject")
//    public String DonationProject(Model model) {
//    	model.addAttribute("currentUrl", "DonationProject");
//        return "DonationProject";
//    }
    @GetMapping("/AccountManagement")
    public String AccountManagement(Model model) {
        model.addAttribute("currentUrl", "AccountManagement");
        return "AccountManagement";
    }
}