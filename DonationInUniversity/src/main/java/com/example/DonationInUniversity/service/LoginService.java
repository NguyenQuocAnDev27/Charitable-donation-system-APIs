package com.example.DonationInUniversity.service;

import com.example.DonationInUniversity.Repository.UserAdminRepository;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserAdminRepository userAdminRepository;
    public User loginAdmin(String email) {
        return  userAdminRepository.findByEmail(email);
    }
}
    