package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.repository.RoleRepository;
import com.example.DonationInUniversity.repository.UserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAdminService {
    @Autowired
    UserAdminRepository userAdminRepository;
    @Autowired
    RoleRepository roleRepository;
    public List<User> getAllUsers() {
        return userAdminRepository.findUsersByIsDeleted(1);
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
}
