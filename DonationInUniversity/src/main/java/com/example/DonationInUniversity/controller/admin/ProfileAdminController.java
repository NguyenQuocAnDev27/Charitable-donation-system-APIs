package com.example.DonationInUniversity.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class ProfileAdminController {
    @GetMapping("profile")
    public String profile() {
        return "pm_profile";
    }
}
