package com.example.DonationInUniversity.Controller.admin;

import com.example.DonationInUniversity.model.CustomUserDetails;
import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ProjectManagerController {
    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;
    @Autowired
    private UserAdminService userAdminService;
    @GetMapping("")
    public String projectHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userAdminService.adminGetUserByUsername(username);
        if (user == null) {
            return "redirect:/admin/login";
        }
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("currentUrl", "DonationProject");
            model.addAttribute("listProjects", projectServiceAdmin.adminGetDonationProjectByManager(userDetails.getUserModel().getUserId()));

        return "ProjectManager/DonationProject";
    }
}
