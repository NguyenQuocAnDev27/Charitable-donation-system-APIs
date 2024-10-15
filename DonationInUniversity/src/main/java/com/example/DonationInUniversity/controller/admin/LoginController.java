package com.example.DonationInUniversity.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class LoginController {
    @GetMapping("login")
    public String login(HttpServletRequest request, Model model) {
        String error = request.getParameter("error");
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }
    @GetMapping("/403")
    public String error403() {
        return "403";
    }
}
