package com.example.DonationInUniversity.controller.web.pm;

import com.example.DonationInUniversity.service.admin.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.mail.MessagingException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Xử lý form gửi email
    @PostMapping("/send-email/{projectId}")
    public String sendEmail(
           @PathVariable int projectId,
            RedirectAttributes model
    ) {
        try {
            // Gửi email
            emailService.sendEmailWithTemplate(projectId);

            // Gửi thông báo thành công tới giao diện
            model.addFlashAttribute("successTransfer", "Email đã được gửi thành công!");
        } catch (MessagingException e) {
            model.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi gửi email.");
        }
        return "redirect:/manager";
    }
}
