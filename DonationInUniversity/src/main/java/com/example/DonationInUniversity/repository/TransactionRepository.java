package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    boolean existsById(String id);

    Page<Transaction> findById(Long id, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.description LIKE %:accountNo%")
    Page<Transaction> findByDonorAccount(String accountNo, Pageable pageable);

    Page<Transaction> findAllByOrderByDateDesc(Pageable pageable);
}
