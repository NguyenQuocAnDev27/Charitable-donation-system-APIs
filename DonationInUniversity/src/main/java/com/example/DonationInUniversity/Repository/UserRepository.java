package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.VerifiedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<VerifiedUser, Long> {
    Optional<VerifiedUser> findByEmail(String email);
}
