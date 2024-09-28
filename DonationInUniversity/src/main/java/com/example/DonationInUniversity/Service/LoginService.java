package com.example.DonationInUniversity.Service;

import com.example.DonationInUniversity.Model.User;
import com.example.DonationInUniversity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;


    public User loginAdmin(String email) {
        return  userRepository.findByEmail(email);
    }
}
