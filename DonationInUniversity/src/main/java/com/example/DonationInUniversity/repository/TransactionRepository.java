package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.Transaction;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("SELECT t FROM Transaction t WHERE " +
            "(:transactionId IS NULL OR t.id = :transactionId) AND " +
            "(:projectId IS NULL OR t.projectId = :projectId) AND " +
            "(:startDate IS NULL OR :endDate IS NULL OR t.date BETWEEN :startDate AND :endDate)")
    Page<Transaction> searchTransactions(
            @Param("transactionId") Long transactionId,
            @Param("projectId") Integer projectId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            Pageable pageable);

    @Query(value = "SELECT * from transaction where project_id=?1", nativeQuery = true)
    List<Transaction> findTransactionsByProjectId(Integer projectId);
}

