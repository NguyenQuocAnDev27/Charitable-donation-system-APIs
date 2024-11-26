package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.TransferApplication;
import com.example.DonationInUniversity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransferApplicationRepository extends JpaRepository<TransferApplication, Integer> {
    @Query(value = "SELECT COUNT(*) FROM transfer_application  WHERE user_id = ?1 AND DATE(created_at) = CURDATE()  ", nativeQuery = true)
    int countTransferRequestsInCurrentWeek(int userId);

    @Query(value = "SELECT * FROM transfer_application WHERE user_id = ?1", nativeQuery = true)
    Page<TransferApplication> findAllByManagerId(int id, Pageable pageable);

    @Query(value = "SELECT * FROM transfer_application WHERE project_id = ?1", nativeQuery = true)
    TransferApplication findByProjectId(int id);
    @Query(value = "SELECT * FROM transfer_application WHERE status = ?1", nativeQuery = true)
    List<TransferApplication> findByStatus(String status);
    boolean existsByUserIdAndProjectIdAndStatus(User userId, DonationProject projectId, String status);
}
