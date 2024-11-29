package com.example.DonationInUniversity.controller.web.admin;

import com.example.DonationInUniversity.service.admin.ProjectServiceAdmin;
import com.example.DonationInUniversity.service.admin.TransferApplicationService;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


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
        List<Object[]> statistics = projectServiceAdmin.countTotalCurrentAmountByMonth();
        model.addAttribute("statistics", statistics);
        model.addAttribute("role", "admin");
        model.addAttribute("stoppedProject", projectServiceAdmin.getProjectStatus("stopped"));
        model.addAttribute("pendingProject", projectServiceAdmin.getProjectStatus("pending"));
        model.addAttribute("completedProject", projectServiceAdmin.getProjectStatus("completed"));
        model.addAttribute("totalDonatedAmount", projectServiceAdmin.totalAmountByStatuses("pending", "completed"));
        model.addAttribute("totalAccount", userAdminService.countByIsDeleted());
        model.addAttribute("totalProject", projectServiceAdmin.countByIsDelete());

        return "pages/index";
    }
    @PostMapping("count")
    @ResponseBody  // Đảm bảo rằng dữ liệu trả về dưới dạng JSON
    public Map<String, Long> countProjectsByMonth(@RequestBody Map<String, Integer> request) {
        int month = request.get("month");  // Nhận giá trị tháng từ body của request

        // Gọi service để đếm số lượng chiến dịch theo tháng đã chọn
        Map<String, Long> projectCounts = projectServiceAdmin.countProjectsByStatusAndMonth(month);
        System.out.println("Month received from frontend: " + month);

        // Trả lại dữ liệu dưới dạng JSON
        return projectCounts;
    }
    }


