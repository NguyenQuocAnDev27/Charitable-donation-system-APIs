package com.example.DonationInUniversity.controller.admin;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.service.admin.UserAdminService;
import com.example.DonationInUniversity.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String homePageAccount(Model model,
                                  @RequestParam(name = "page", defaultValue = "1") int pageNo,
                                  @RequestParam(name = "size", defaultValue = "5") int pageSize) {
        Page<User> page = userAdminService.getAllUsers(pageNo,pageSize);
        List<User> userList = page.getContent();
        List<Role> roles= userAdminService.getAllRole();
        model.addAttribute("listUsers", userList);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listRoles", roles);
        model.addAttribute("currentUrl", "AccountManagement");
        model.addAttribute("users", new User());
        return "AccountManagement";
    }
    @PostMapping("saveOrUpdateAccount")
    public String saveOrUpdateAccount(@ModelAttribute("users") User users, RedirectAttributes redirectAttributes) {
     try{
         if(users.getUserId() == null){
             String encodedPassword = passwordEncoder.encode(users.getPasswordHash());
             users.setPasswordHash(encodedPassword);
             users.setIsDeleted(1);
             userAdminService.addUser(users);
             redirectAttributes.addFlashAttribute("successMessage", "Thêm tài khoản thành công");
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
             redirectAttributes.addFlashAttribute("successMessage", "Sửa tài khoản thành công");
         }
     }
     catch(Exception e){
         redirectAttributes.addFlashAttribute("successMessage", "Có lỗi xảy ra trong lúc xử lý");
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
    public String deleteUser(@PathVariable int id,RedirectAttributes redirectAttributes) {
        try{
            this.userAdminService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa tài khoản thành công");
        }
      catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Xóa tài khoản thất bại");
      }
        return "redirect:/admin/AccountManagement";
    }
}
