package com.example.DonationInUniversity.controller.web.global;

import com.example.DonationInUniversity.controller.web.pm.TagsController;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("login")
    public String login(HttpServletRequest request, Model model) {
        try {
            String error = request.getParameter("error");
            if (error != null) {
                model.addAttribute("error", true);
            }
            return "pages/authPage/login";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "pages/errorPage/404";
        }
    }
    @GetMapping("/404")
    public String error() {
        return "pages/errorPage/404";
    }
}
