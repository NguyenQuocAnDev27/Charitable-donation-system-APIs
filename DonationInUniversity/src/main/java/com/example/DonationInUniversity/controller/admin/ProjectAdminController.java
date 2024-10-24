package com.example.DonationInUniversity.controller.admin;

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
    public String projectHomePage(Model model,
                                  @RequestParam(name = "page", defaultValue = "1") int pageNo,
                                  @RequestParam(name = "size", defaultValue = "5") int pageSize) {
        // Lấy danh sách các quản lý dự án
        List<User> projectManagers = userAdminService.getProjectManager();

        // Lấy dữ liệu phân trang từ service
        Page<DonationProject> page = projectServiceAdmin.getAllProjects(pageNo, pageSize);
        List<DonationProject> listProjects = page.getContent();

        // Thêm các thông tin cần thiết vào model
        model.addAttribute("currentUrl", "DonationProject");
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("projectManagers", projectManagers);
        model.addAttribute("project", new DonationProject());

        return "DonationProject";
    }
    @GetMapping("DonationProject/{id}")
    public String getDonationProject(Model model, @PathVariable int id) {
        Optional<DonationProject> project = projectServiceAdmin.getProjectById(id);
        model.addAttribute("project",project);
        return "DonationProject";
    }
    @PostMapping("saveOrUpdateProject")
    public String addOrUpdateProject(@ModelAttribute("project") DonationProject project, RedirectAttributes redirectAttributes) {
        try{
            if(project.getProjectId() == null){
                project.setIsDeleted(1);
                projectServiceAdmin.addProject(project);
                redirectAttributes.addFlashAttribute("successMessage", "Thêm dự án thành công");
            }
            else {
                project.setIsDeleted(1);
                projectServiceAdmin.updateProject(project);
                redirectAttributes.addFlashAttribute("successMessage", "Sửa dự án thành công");
            }
        }
       catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra trong lúc xử lý");
       }
        return "redirect:/admin/DonationProject";
    }
    @PostMapping("deleteProject/{id}")
    public String deleteProject(@PathVariable int id,RedirectAttributes redirectAttributes) {
        try{
            this.projectServiceAdmin.deleteProject(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa dự án thành công");

        }
      catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Xóa dự án thất bại");
        }
        return "redirect:/admin/DonationProject";
    }
}
