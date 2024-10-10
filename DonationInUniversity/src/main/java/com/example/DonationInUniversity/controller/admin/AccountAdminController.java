package com.example.DonationInUniversity.controller.admin;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import com.example.DonationInUniversity.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/")
public class AccountAdminController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAdminService userAdminService;
    @GetMapping("AccountManagement")
    public String homePageAccount(Model model) {
        List<User> users = userAdminService.getAllUsers();
        List<Role> roles= userAdminService.getAllRole();
        model.addAttribute("listUsers", users);
        model.addAttribute("listRoles", roles);
        model.addAttribute("currentUrl", "AccountManagement");
        model.addAttribute("users", new User());
        return "AccountManagement";
    }
    @PostMapping("saveOrUpdateAccount")
    public String saveOrUpdateAccount(@ModelAttribute("users") User users) {
      if(users.getUserId() == null){
          String encodedPassword = passwordEncoder.encode(users.getPasswordHash());
          users.setPasswordHash(encodedPassword);
          users.setIsDeleted(1);
          userAdminService.addUser(users);
      }
      else{
          Optional<User> existingUser = userAdminService.getUserById(users.getUserId());
          if (users.getPasswordHash() == null || users.getPasswordHash().isEmpty()) {
              // Giữ nguyên mật khẩu cũ
              users.setPasswordHash(existingUser.get().getPasswordHash());
          } else {
              // Nếu mật khẩu mới được nhập, mã hóa mật khẩu mới
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
        Optional<User> user = userAdminService.getUserById(id);
        model.addAttribute("user", user);
        return "AccountManagement";
    }
    @PostMapping("deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        this.userAdminService.deleteUser(id);
        return "redirect:/admin/AccountManagement";
    }
}
