package com.example.DonationInUniversity.controller.web.pm;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.repository.ProjectDetailTextRepository;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.api.TagService;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import com.example.DonationInUniversity.service.api.ProjectService;
import com.example.DonationInUniversity.service.api.ProjectTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager")
public class ProjectManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectManagerController.class);

    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;

    @Autowired
    private ProjectTagService projectTagService;

    @Autowired
    private ProjectDetailTextRepository projectDetailTextRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserAdminService userAdminService;

    @Value("${server.url}")
    private String serverUrl;

    @RequestMapping("/**")
    public String fallback() {
        return "pages/errorPage/404"; // Path to your custom 404 page
    }

    // Project Management Home Page
    @GetMapping({"", "/", "projects"})
    public String projectHomePage(Model model, @RequestParam(name = "page", defaultValue = "1") int pageNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userAdminService.adminGetUserByUsername(username);
        if (user == null) {
            return "redirect:/admin/login";
        }

        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Page<DonationProject> pageDonation = projectServiceAdmin.getAllDonationProjectByManager(userDetails.getUserModel().getUserId(), pageNo);

            model.addAttribute("currentUrl", "DonationProject");
            model.addAttribute("totalPage", pageDonation.getTotalPages());
            model.addAttribute("listProjects", pageDonation);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("project", new DonationProject());

        } catch (Exception e) {
            // Log the error
            System.err.println("Error fetching projects for manager: " + e.getMessage());
            model.addAttribute("listProjects", Page.empty());  // Return an empty page
            model.addAttribute("totalPage", 0);
            model.addAttribute("currentPage", 1);
        }

        return "ProjectManager/DonationProject";
    }


    // Save or Update Project
    @PostMapping("/saveOrUpdateProject")
    public String addOrUpdateProject(@ModelAttribute("project") DonationProject project) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userAdminService.adminGetUserByUsername(username);
        project.setIsDeleted(1);
        project.setProjectManager(user);

        if (project.getProjectId() == null) {
            projectServiceAdmin.addProject(project);
        } else {
            projectServiceAdmin.updateProject(project);
        }
        return "redirect:/manager";
    }

    // Delete Project
    @PostMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable int id) {
        projectServiceAdmin.deleteProject(id);
        return "redirect:/manager";
    }
}
