package com.example.DonationInUniversity.Controller.Admin;

import com.example.DonationInUniversity.Model.DonationProject;
import com.example.DonationInUniversity.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @GetMapping("projects")
    public String projectHomePage(Model model) {
        model.addAttribute("listProjects", projectService.getAllProjects());
        return "DonationProject";
    }
    @PostMapping("saveProject")
    public String addProject(@ModelAttribute("project")DonationProject project) {
       this.projectService.addProject(project);
        return "redirect:/DonationProject";
    }
    @GetMapping("deleteProject/{id}")
    public String deleteProject(@PathVariable(value = "id") int id) {
       this.projectService.deleteProject(id);
       return "redirect:/DonationProject";
    }
}
