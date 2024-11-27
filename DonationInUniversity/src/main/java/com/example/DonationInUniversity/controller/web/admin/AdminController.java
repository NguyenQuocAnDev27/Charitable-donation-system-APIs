package com.example.DonationInUniversity.controller.web.admin;

import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.admin.TransferApplicationService;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/")
public class AdminController {
    @Autowired
    private ProjectServiceAdmin projectServiceAdmin;
    @Autowired
    private UserAdminService userAdminService;
    @Autowired
    private TransferApplicationService transferApplicationService;
    @GetMapping("")
    public String homepage(Model model) {
        model.addAttribute("role", "admin");
        model.addAttribute("currentUrl", "Report");
        model.addAttribute("stoppedProject", projectServiceAdmin.getProjectStatus("stopped"));
        model.addAttribute("pendingProject", projectServiceAdmin.getProjectStatus("pending"));
        model.addAttribute("completedProject", projectServiceAdmin.getProjectStatus("completed"));
        model.addAttribute("project_manager", userAdminService.countByRole("project_manager"));
        model.addAttribute("normal", userAdminService.countByRole("normal_user"));
        model.addAttribute("guest", userAdminService.countByRole("guest"));
        model.addAttribute("accept", transferApplicationService.countByStatus("accept"));
        model.addAttribute("decline", transferApplicationService.countByStatus("decline"));
        model.addAttribute("totalDonatedAmount", projectServiceAdmin.totalAmountByStatuses("pending", "completed"));
        model.addAttribute("totalAccount", userAdminService.countByIsDeleted());
        model.addAttribute("totalTransfer", transferApplicationService.count());
        model.addAttribute("totalProject", projectServiceAdmin.countByIsDelete());

        return "pages/index";
    }

}
