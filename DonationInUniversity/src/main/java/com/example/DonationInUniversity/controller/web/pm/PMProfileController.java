package com.example.DonationInUniversity.controller.web.pm;

import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.model.UserBankInfo;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import com.example.DonationInUniversity.service.admin.UserBankInfoAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class PMProfileController {
    @Autowired
    private UserBankInfoAdminService userBankInfoAdminService;
    @Autowired
    private UserAdminService userService;

    @GetMapping("/profile")
    public String profile(Model model) {
        // Lấy thông tin người dùng đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }
        }

        // Lấy đối tượng User dựa vào username
        User currentUser = userService.adminGetUserByUsername(username);

        // Lấy thông tin UserBankInfo từ CSDL
        UserBankInfo existingProfile = userBankInfoAdminService.findByUser(currentUser);

        // Đưa dữ liệu vào model nếu đã có
        if (existingProfile != null) {
            model.addAttribute("profile", existingProfile);
            model.addAttribute("bank_id", existingProfile.getBank_id());
            model.addAttribute("account_no",existingProfile.getAccount_no());
        } else {
            model.addAttribute("profile", new UserBankInfo()); // Nếu không có, tạo mới đối tượng rỗng
        }
        model.addAttribute("role","project_manager");
        return "pages/userManagementPage/Profile"; // Trả về trang Thymeleaf
    }
    @PostMapping("saveOrUpdateProfile")
    public String saveOrUpdateProfile(UserBankInfo profile) {
        // Lấy thông tin người dùng đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername(); // Lấy username của người dùng
            } else {
                username = principal.toString();
            }
        }

        // Lấy đối tượng User dựa vào username
        User currentUser = userService.adminGetUserByUsername(username);

        // Kiểm tra xem người dùng đã có thông tin UserBankInfo chưa
        UserBankInfo existingProfile = userBankInfoAdminService.findByUser(currentUser);

        if (existingProfile != null) {
            // Người dùng đã có thông tin => cập nhật thông tin hiện tại
            existingProfile.setBank_id(profile.getBank_id());
            existingProfile.setAccount_no(profile.getAccount_no());
            userBankInfoAdminService.addProfile(existingProfile); // Cập nhật thông tin
        } else {
            // Người dùng chưa có thông tin => tạo mới
            profile.setUser(currentUser); // Đặt user vào profile mới
            userBankInfoAdminService.addProfile(profile); // Lưu mới
        }
        return "redirect:/manager";  // Điều hướng sau khi lưu hoặc cập nhật
    }
}
