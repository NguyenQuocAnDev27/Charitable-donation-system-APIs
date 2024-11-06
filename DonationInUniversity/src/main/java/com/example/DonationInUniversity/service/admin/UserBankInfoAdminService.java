package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.model.UserBankInfo;
import com.example.DonationInUniversity.repository.UserBankInfoAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBankInfoAdminService {
    @Autowired
    private UserBankInfoAdminRepository userBankInfoAdminRepository;
    public UserBankInfo addProfile(UserBankInfo userBankInfo) {
        return userBankInfoAdminRepository.save(userBankInfo);
    }
    public UserBankInfo updateProfile(UserBankInfo userBankInfo) {
        return userBankInfoAdminRepository.save(userBankInfo);
    }

    public UserBankInfo findByUser(User currentUser) {
        return userBankInfoAdminRepository.findByUser(currentUser);
    }
}
