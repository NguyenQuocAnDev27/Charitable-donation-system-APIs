package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.TransferApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransferApplicationRepository extends JpaRepository<TransferApplication, Integer> {
    @Query(value="SELECT COUNT(*) FROM transfer_application  WHERE user_id = ?1 AND created_at BETWEEN ?2 AND ?3 ", nativeQuery = true)
    int countTransferRequestsInCurrentWeek(int userId, LocalDate startOfWeek, LocalDate endOfWeek);
    @Query(value = "SELECT * FROM transfer_application WHERE user_id = ?1", nativeQuery = true)
    Page<TransferApplication> findAllByManagerId(int id, Pageable pageable);
}
