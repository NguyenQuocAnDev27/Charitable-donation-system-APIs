package com.example.DonationInUniversity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class HomeController {

    @GetMapping("")
    public String homepage() {
        return "index";
    }
    @GetMapping("AccountManagement")
    public String AccountManagement(Model model) {
        model.addAttribute("currentUrl", "AccountManagement");
        return "AccountManagement";
    }
}
