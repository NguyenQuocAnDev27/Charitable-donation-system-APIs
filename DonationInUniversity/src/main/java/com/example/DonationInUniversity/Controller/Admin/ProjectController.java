package com.example.DonationInUniversity.Controller.Admin;

import com.example.DonationInUniversity.Model.DonationProject;
import com.example.DonationInUniversity.Service.ProjectServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/")
public class ProjectController {
    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;
    @GetMapping("DonationProject")
    public String projectHomePage(Model model) {
        model.addAttribute("currentUrl", "DonationProject");
        model.addAttribute("listProjects", projectServiceAdmin.getAllProjects());
        model.addAttribute("project", new DonationProject());
        return "DonationProject";
    }
    @GetMapping("DonationProject/{id}")
    public String getDonationProject(Model model, @PathVariable int id) {
        DonationProject project = projectServiceAdmin.getProjectById(id);
        model.addAttribute("project",project);
        return "DonationProject";
    }
    @PostMapping("saveOrUpdateProject")
    public String addOrUpdateProject(@ModelAttribute("project") DonationProject project) {
        if(project.getProjectId() == null){
            projectServiceAdmin.addProject(project);
        }
        else {
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
