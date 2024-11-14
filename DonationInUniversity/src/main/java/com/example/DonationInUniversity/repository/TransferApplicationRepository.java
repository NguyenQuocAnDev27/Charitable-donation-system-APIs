package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.TransferApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferApplicationRepository extends JpaRepository<TransferApplication, Integer> {
}
