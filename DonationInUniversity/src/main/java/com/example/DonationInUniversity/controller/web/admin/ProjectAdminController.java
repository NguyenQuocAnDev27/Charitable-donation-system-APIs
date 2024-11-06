package com.example.DonationInUniversity.controller.web.admin;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/")
public class ProjectAdminController {
    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;
    @Autowired
    private UserAdminService userAdminService;
    @GetMapping("DonationProject")
    public String projectHomePage(Model model) {
        List<User> projectManagers = userAdminService.getProjectManager();
        model.addAttribute("currentUrl", "DonationProject");
        model.addAttribute("listProjects", projectServiceAdmin.getAllProjects());
        model.addAttribute("projectManagers", projectManagers);
        model.addAttribute("role", "admin");
        model.addAttribute("project", new DonationProject());
        return "pages/projectsManagementPage/project_management";
    }
    @GetMapping("DonationProject/{id}")
    public String getDonationProject(Model model, @PathVariable int id) {
        Optional<DonationProject> project = projectServiceAdmin.getProjectById(id);
        model.addAttribute("project",project);
        return "pages/projectsManagementPage/project_management";
    }
    @PostMapping("saveOrUpdateProject")
    public String addOrUpdateProject(@ModelAttribute("project") DonationProject project) {
        if(project.getProjectId() == null){
            project.setIsDeleted(1);
            projectServiceAdmin.addProject(project);
        }
        else {
            project.setIsDeleted(1);
            projectServiceAdmin.updateProject(project);
        }
        return "redirect:/admin/DonationProject";
    }
    @PostMapping("deleteProject/{id}")
    public String deleteProject(@PathVariable int id) {
        this.projectServiceAdmin.deleteProject(id);
        return "redirect:/admin/DonationProject";
    }
}
