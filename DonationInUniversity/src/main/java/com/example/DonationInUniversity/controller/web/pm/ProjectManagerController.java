package com.example.DonationInUniversity.controller.web.pm;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.repository.ProjectDetailTextRepository;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.admin.TransferApplicationService;
import com.example.DonationInUniversity.service.api.TagService;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import com.example.DonationInUniversity.service.api.ProjectService;
import com.example.DonationInUniversity.service.api.ProjectTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @Autowired
    private TransferApplicationService transferApplicationService;

    @RequestMapping("/**")
    public String fallback() {
        return "pages/errorPage/404"; // Path to your custom 404 page
    }

    // Project Management Home Page
    @GetMapping({ "", "/", "projects" })
    public String projectHomePage(Model model, @RequestParam(name = "page", defaultValue = "1") int pageNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userAdminService.adminGetUserByUsername(username);
        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);
        if (user == null) {
            return "redirect:/admin/login";
        }

        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            int transferRequestCount = transferApplicationService
                    .countTransferRequestsInCurrentWeek(userDetails.getUserModel().getUserId());
            boolean isRequestDisabled = transferRequestCount >= 3;
            model.addAttribute("isRequestDisabled", isRequestDisabled);
            List<DonationProject> listProjects = projectServiceAdmin.getAllProjectsForManager(userDetails.getUserModel().getUserId()); // Lấy tất cả dự án
            Map<Integer, Boolean> projectTransferStatus = new HashMap<>();

            for (DonationProject project : listProjects) {
                boolean hasTransfer = transferApplicationService.existsByUserIdAndProjectId(userDetails.getUserModel(), project);
                projectTransferStatus.put(project.getProjectId(), hasTransfer);
            }
            model.addAttribute("projectTransferStatus", projectTransferStatus);
            Page<DonationProject> pageDonation = projectServiceAdmin
                    .getAllDonationProjectByManager(userDetails.getUserModel().getUserId(), pageNo);
            model.addAttribute("role", "project_manager");
            model.addAttribute("currentUrl", "DonationProject");
            model.addAttribute("totalPage", pageDonation.getTotalPages());
            model.addAttribute("listProjects", pageDonation);
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("project", new DonationProject());
            model.addAttribute("transfer", new TransferApplication());

        } catch (Exception e) {
            // Log the error
            System.err.println("Error fetching projects for manager: " + e.getMessage());
            model.addAttribute("role", "project_manager");
            model.addAttribute("listProjects", Page.empty()); // Return an empty page
            model.addAttribute("totalPage", 0);
            model.addAttribute("currentPage", 0);
            model.addAttribute("project", new DonationProject());
            model.addAttribute("transfer", new TransferApplication());
        }
        return "pages/projectsManagementPage/project_management";
    }

    // Save or Update Project
    @PostMapping("/saveOrUpdateProject")
    public String addOrUpdateProject(@ModelAttribute("project") DonationProject project) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userAdminService.adminGetUserByUsername(username);
            project.setIsDeleted(1);
            project.setProjectManager(user);
            if (project.getProjectId() == null) {
                project.setCurrentAmount(new BigDecimal(0));
                projectServiceAdmin.addProject(project);
            } else {
                projectServiceAdmin.updateProject(project);
            }
            return "redirect:/manager";
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return "pages/errorPage/404";
        }
    }
    // Delete Project
    @PostMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable int id) {
        try {
            projectServiceAdmin.deleteProject(id);
            return "redirect:/manager";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "pages/errorPage/404";
        }
    }
}
