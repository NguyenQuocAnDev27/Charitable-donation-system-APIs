package com.example.DonationInUniversity.Repository;

import com.example.DonationInUniversity.Dto.AuthDTO;
import com.example.DonationInUniversity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.passwordHash = ?2")
    List<User> verifyAccount(String email, String passwordHash);
}
