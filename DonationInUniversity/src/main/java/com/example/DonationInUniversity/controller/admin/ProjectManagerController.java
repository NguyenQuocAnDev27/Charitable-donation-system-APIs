package com.example.DonationInUniversity.controller.admin;


import com.example.DonationInUniversity.model.CustomUserDetails;
import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.model.UserBankInfo;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.repository.ProjectTagRepository;

import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.admin.TagService;
import com.example.DonationInUniversity.service.admin.UserAdminService;

import com.example.DonationInUniversity.service.admin.UserBankInfoAdminService;

import com.example.DonationInUniversity.service.api.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ProjectManagerController {

    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;

    @Autowired
    private ProjectTagRepository projectTagRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private UserBankInfoAdminService userBankInfoAdminService;


    // Project Management Home Page

    @GetMapping("")
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
    public String addOrUpdateProject(@ModelAttribute("project") DonationProject project, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userAdminService.adminGetUserByUsername(username);
        UserBankInfo bankInfo = userBankInfoAdminService.findByUser(user);
        if (bankInfo == null || bankInfo.getAccount_no() == null || bankInfo.getAccount_no().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn phải nhập thông tin tài khoản ngân hàng trước khi tạo dự án.");
            return "redirect:/manager"; // Điều hướng người dùng về trang nhập thông tin tài khoản ngân hàng
        }
        if (project.getProjectId() == null) {
            try {
                project.setIsDeleted(1);
                project.setProjectManager(user);
                projectServiceAdmin.addProject(project);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                project.setIsDeleted(1);
                project.setProjectManager(user);
                projectServiceAdmin.updateProject(project);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/manager";
    }
    @PostMapping("/deleteProject/{id}")
    public String deleteProject ( @PathVariable int id){
        projectServiceAdmin.deleteProject(id);
        return "redirect:/manager";
    }
    // Tags Management Page
    @GetMapping("/TagsManagement")
    public String tagsManagementPage (Model model,@RequestParam(name = "page", defaultValue = "1") int pageNo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userAdminService.adminGetUserByUsername(username);

        if (user == null) {
            return "redirect:/admin/login";
        }

        try {
            // Fetch tags with associated projects for the current manager
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            List<ProjectTagDisplayTable> projectTagDisplayTableList = projectServiceAdmin.getAllProjectTagsByManager(userDetails.getUserModel().getUserId(), PageRequest.of(pageNo - 1, 10));

            // Fetch all available projects for the dropdown
            List<DonationProject> projects = projectServiceAdmin.getAllProjectsForManager(userDetails.getUserModel().getUserId());

            model.addAttribute("currentUrl", "TagsManagement");
            model.addAttribute("totalPage", projectTagDisplayTableList.size()); // Assuming pagination
            model.addAttribute("projectTagDisplayTableList", projectTagDisplayTableList); // The combined data for display
            model.addAttribute("projects", projects); // Add list of projects for the dropdown
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("tag", new Tag());

        } catch (Exception e) {
            System.err.println("Error fetching tags: " + e.getMessage());
            model.addAttribute("projectTagDisplayTableList", List.of());  // Return an empty list
            model.addAttribute("totalPage", 0);
            model.addAttribute("currentPage", 1);
        }

        return "ProjectManager/TagsManagement";
    }


    // Save or Update Tag
    @PostMapping("/saveOrUpdateTag")
    public String addOrUpdateTag (
            @ModelAttribute("tag") Tag tag,
            @RequestParam("projectId") Integer projectId){

        // Fetch the selected project by ID
        DonationProject project = projectService.getProjectById(projectId);

        if (project == null) {
            // Handle case where project is not found, e.g., log an error or redirect with a message
            return "redirect:/manager/TagsManagement?error=ProjectNotFound";
        }

        // Save the tag and associate it with the project
        tagService.saveTagWithProject(tag, project.getProjectId());

        return "redirect:/manager/TagsManagement";
    }

    // Delete Tag
    @PostMapping("/deleteTag/{id}")
    public String deleteTag ( @PathVariable int id){
        tagService.deleteTag(id);
        return "redirect:/manager/TagsManagement";
    }
}
