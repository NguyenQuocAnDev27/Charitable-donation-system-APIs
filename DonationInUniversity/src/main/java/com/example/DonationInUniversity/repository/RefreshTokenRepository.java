package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    // Tìm RefreshToken theo token
    Optional<RefreshToken> findByToken(String token);

    // Xóa RefreshToken theo token
    void deleteByToken(String token);
}
