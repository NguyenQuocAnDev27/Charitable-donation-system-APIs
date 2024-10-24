package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.model.UserBankInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBankInfoAdminRepository extends JpaRepository<UserBankInfo,Integer> {
    UserBankInfo findByUser(User user);
}
