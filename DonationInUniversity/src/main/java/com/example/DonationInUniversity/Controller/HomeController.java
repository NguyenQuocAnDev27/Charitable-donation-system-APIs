package com.example.DonationInUniversity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/DonationProject")
    public String DonationProject(Model model) {
    	model.addAttribute("currentUrl", "DonationProject");
        return "DonationProject";
    }
    @GetMapping("/AccountManagement")
    public String AccountManagement(Model model) {
    	model.addAttribute("currentUrl", "AccountManagement");
        return "AccountManagement";
    }
}
