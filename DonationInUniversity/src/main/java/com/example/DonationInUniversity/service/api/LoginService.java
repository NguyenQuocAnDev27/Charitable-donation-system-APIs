package com.example.DonationInUniversity.service.api;

import com.example.DonationInUniversity.repository.UserAdminRepository;
import com.example.DonationInUniversity.model.User;
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
    