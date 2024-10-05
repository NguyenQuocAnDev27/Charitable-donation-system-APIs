package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAdminRepository extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);
}
