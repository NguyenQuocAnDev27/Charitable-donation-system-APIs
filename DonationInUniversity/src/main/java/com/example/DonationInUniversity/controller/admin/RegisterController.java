package com.example.DonationInUniversity.controller.admin;

import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.model.VerifiedUser;
import com.example.DonationInUniversity.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class RegisterController {
    @Autowired
    private UserService userService;
    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("register")
    public String register(@ModelAttribute("user") User user, Model model) {
        VerifiedUser newUser =new VerifiedUser();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPasswordHash(user.getPasswordHash());
        newUser.setPhoneNumber(user.getPhoneNumber());
        userService.saveUser(newUser,"admin");
        return "redirect:login";
    }
}
