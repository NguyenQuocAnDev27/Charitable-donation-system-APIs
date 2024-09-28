package com.example.DonationInUniversity.controller.admin;

import com.example.DonationInUniversity.service.DonationProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class DonationProjectController {
    DonationProjectService donationProjectService;

    @GetMapping("/DonationProject")
    public String getDonationProject(Model model) {
        model.addAttribute("currentUrl", "DonationProject");
        model.addAttribute("projectList", donationProjectService.getAllDonationProjects());
        return "DonationProject";
    }

    @PostMapping("/DonationProject")
    public String postDonationProject() {
        return "DonationProject";
    }

    @PutMapping("/DonationProject")
    public String putDonationProject() {
        return "DonationProject";
    }
}
