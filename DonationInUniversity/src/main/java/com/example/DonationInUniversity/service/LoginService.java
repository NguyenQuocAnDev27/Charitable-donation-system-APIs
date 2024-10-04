package com.example.DonationInUniversity.service;

import com.example.DonationInUniversity.repository.UserAdminRepository;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserAdminRepository userAdminRepository;
    public User loginAdmin(String email) {
        return  userAdminRepository.findByEmail(email);
    }
}
    