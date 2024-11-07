package com.example.DonationInUniversity.controller.web.admin;

import com.example.DonationInUniversity.controller.web.pm.TagsController;
import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/")
public class AccountAdminController {
    private static final Logger logger = LoggerFactory.getLogger(AccountAdminController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAdminService userAdminService;

    @GetMapping("AccountManagement")
    public String homePageAccount(Model model,@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        try{
            Page<User> users = userAdminService.getAllUsers(pageNo);
            List<Role> roles= userAdminService.getAllRole();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            model.addAttribute("listUsers", users);
            model.addAttribute("listRoles", roles);
            model.addAttribute("role", "admin");
            model.addAttribute("currentUrl", "AccountManagement");
            model.addAttribute("totalPage", users.getTotalPages());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("users", new User());
            model.addAttribute("currentUsername", currentUsername);
        }
        catch(Exception e){
            System.err.println("Error fetching projects for manager: " + e.getMessage());
            model.addAttribute("role", "project_manager");
            model.addAttribute("listProjects", Page.empty()); // Return an empty page
            model.addAttribute("totalPage", 0);
            model.addAttribute("currentPage", 0);
        }
        return "pages/userManagementPage/user_management";
    }
    @PostMapping("saveOrUpdateAccount")
    public String saveOrUpdateAccount(@ModelAttribute("users") User users) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Kiểm tra nếu là tài khoản của chính người dùng, giữ quyền hiện tại
        if (users.getUserId() != null && users.getEmail().equals(currentUsername)) {
            Optional<User> existingUser = userAdminService.getUserById(users.getUserId());

            if (existingUser.isPresent()) {
                users.setRole(existingUser.get().getRole()); // Giữ quyền cũ không thay đổi
            }
        }

        // Nếu là tài khoản mới, mã hóa mật khẩu và tạo tài khoản mới
        if (users.getUserId() == null) {
            String encodedPassword = passwordEncoder.encode(users.getPasswordHash());
            users.setPasswordHash(encodedPassword);
            users.setIsDeleted(1);
            userAdminService.addUser(users);
        } else {
            // Nếu mật khẩu không được cung cấp, giữ mật khẩu cũ
            Optional<User> existingUser = userAdminService.getUserById(users.getUserId());
            if (users.getPasswordHash() == null || users.getPasswordHash().isEmpty()) {
                users.setPasswordHash(existingUser.get().getPasswordHash());
            } else {
                String encodedPassword = passwordEncoder.encode(users.getPasswordHash());
                users.setPasswordHash(encodedPassword);
            }
            users.setIsDeleted(1);
            userAdminService.updateUser(users);
        }

        return "redirect:/admin/AccountManagement";
    }


    @GetMapping("AccountManagement/{id}")
    public String showAccount(@PathVariable int id, Model model) {
        try {
            Optional<User> user = userAdminService.getUserById(id);
            model.addAttribute("user", user);
            // return "AccountManagement";
            return "pages/userManagementPage/user_management";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "pages/errorPage/404";
        }
    }
    @PostMapping("deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        try {
            this.userAdminService.deleteUser(id);
            return "redirect:/admin/AccountManagement";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "pages/errorPage/404";
        }
    }
}
