package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.repository.RoleRepository;
import com.example.DonationInUniversity.repository.UserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAdminService {
    @Autowired
    UserAdminRepository userAdminRepository;
    @Autowired
    RoleRepository roleRepository;
    public Page<User> getAllUsers(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo-1, 5);
        return userAdminRepository.findUsersByIsDeleted(1,pageable);
    }
    public Optional<User> getUserById(int id) {
        return  userAdminRepository.findById(id);
    }
    public User addUser(User user) {
        return userAdminRepository.save(user);
    }
    public User updateUser(User user) {
        return userAdminRepository.save(user);
    }
    public void deleteUser(int id) {
        Optional<User> userOptional = userAdminRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsDeleted(0); // Đặt isDeleted = 1 để đánh dấu là đã xóa
            userAdminRepository.save(user);
        }
    }
    public List<User> getProjectManager () {
        Role roleId = new Role();
        roleId.setRoleId(2);
        return userAdminRepository.findByRole(roleId);
    }
    public List<Role> getAllRole () {
        return roleRepository.findAll();
    }
    public long countByRole(String roleName) {
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        return userAdminRepository.findByRole(role).size();
    }
    public User adminGetUserByUsername(String username) {
        return userAdminRepository.findByEmail(username);
    }
    public int countByIsDeleted() {
        return  userAdminRepository.countByIsDeleted(1);
    }
}
