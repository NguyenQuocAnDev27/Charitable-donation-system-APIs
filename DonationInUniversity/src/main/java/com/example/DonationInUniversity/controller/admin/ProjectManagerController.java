package com.example.DonationInUniversity.controller.admin;

import com.example.DonationInUniversity.model.CustomUserDetails;
import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.model.UserBankInfo;
import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import com.example.DonationInUniversity.service.admin.UserBankInfoAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager")
public class ProjectManagerController {
    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;
    @Autowired
    private UserAdminService userAdminService;
    @Autowired
    private UserBankInfoAdminService userBankInfoAdminService;
    @GetMapping("")
    public String projectHomePage(Model model, @RequestParam(name = "page", defaultValue = "1") int pageNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userAdminService.adminGetUserByUsername(username);
        if (user == null) {
            return "redirect:/admin/login";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Page<DonationProject> pageDonation= this.projectServiceAdmin.getAllDonationProjectByManager(userDetails.getUserModel().getUserId(), pageNo);
        model.addAttribute("currentUrl", "DonationProject");
        model.addAttribute("totalPage", pageDonation.getTotalPages());
        model.addAttribute("listProjects", pageDonation);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("project", new DonationProject());
        return "ProjectManager/DonationProject";
    }
    @PostMapping("/saveOrUpdateProject")
    public String addOrUpdateProject(@ModelAttribute("project") DonationProject project, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userAdminService.adminGetUserByUsername(username);
        UserBankInfo bankInfo = userBankInfoAdminService.findByUser(user);
        if (bankInfo == null || bankInfo.getAccount_no() == null || bankInfo.getAccount_no().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn phải nhập thông tin tài khoản ngân hàng trước khi tạo dự án.");
            return "redirect:/manager"; // Điều hướng người dùng về trang nhập thông tin tài khoản ngân hàng
        }
        if(project.getProjectId() == null){
            try {
                project.setIsDeleted(1);
                project.setProjectManager(user);
                projectServiceAdmin.addProject(project);
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        else {
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
    public String deleteProject(@PathVariable int id) {
        this.projectServiceAdmin.deleteProject(id);
        return "redirect:/manager";
    }
}