package com.example.DonationInUniversity.controller.web.admin;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String projectHomePage(Model model,@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        List<User> projectManagers = userAdminService.getProjectManager();
        Page<DonationProject> projects = projectServiceAdmin.getAllProjects(pageNo);
        model.addAttribute("currentUrl", "DonationProject");
        model.addAttribute("listProjects",projects );
        model.addAttribute("projectManagers", projectManagers);
        model.addAttribute("role", "admin");
        model.addAttribute("project", new DonationProject());
        model.addAttribute("totalPage", projects.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "pages/projectsManagementPage/project_admin_management";
    }
    @GetMapping("DonationProject/{id}")
    public String getDonationProject(Model model, @PathVariable int id) {
        Optional<DonationProject> project = projectServiceAdmin.getProjectById(id);
        model.addAttribute("project",project);
        return "pages/projectsManagementPage/project_management";
    }
    @PostMapping("saveOrUpdateProject")
    public String addOrUpdateProject(@ModelAttribute("project") DonationProject project, RedirectAttributes redirectAttributes) {
        if(project.getProjectId() == null){
            project.setIsDeleted(1);
            projectServiceAdmin.addProject(project);
            redirectAttributes.addFlashAttribute("success","Lưu chiến dịch thành công");
        }
        else {
            project.setIsDeleted(1);
            projectServiceAdmin.updateProject(project);
            redirectAttributes.addFlashAttribute("success","Cập nhật chiến dịch thành công");
        }
        return "redirect:/admin/DonationProject";
    }
    @PostMapping("deleteProject/{id}")
    public String deleteProject(@PathVariable int id,RedirectAttributes redirectAttributes) {
        this.projectServiceAdmin.deleteProject(id);
        redirectAttributes.addFlashAttribute("success","Xóa chiến dịch thành công");
        return "redirect:/admin/DonationProject";
    }
}
