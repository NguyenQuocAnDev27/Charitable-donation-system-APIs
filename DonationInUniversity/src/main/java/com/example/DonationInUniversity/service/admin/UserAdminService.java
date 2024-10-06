package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.repository.UserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdminService {
    @Autowired
    UserAdminRepository userAdminRepository;

    public List<User> getProjectManager() {
        Role roleId = new Role();
        roleId.setRoleId(2);
        return userAdminRepository.findByRole(roleId);
    }
}
