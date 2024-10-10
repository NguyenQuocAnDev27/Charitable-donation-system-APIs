package com.example.DonationInUniversity.controller.admin;

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
    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}
