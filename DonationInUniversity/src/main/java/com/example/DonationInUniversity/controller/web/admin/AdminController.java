package com.example.DonationInUniversity.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/")
public class AdminController {

    @GetMapping("")
    public String homepage(Model model) {
        model.addAttribute("role", "admin");
        return "pages/index";
    }

}
