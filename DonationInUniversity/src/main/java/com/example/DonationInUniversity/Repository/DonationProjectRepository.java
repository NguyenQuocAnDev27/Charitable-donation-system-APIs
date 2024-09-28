package com.example.DonationInUniversity.Repository;

import com.example.DonationInUniversity.Model.DonationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationProjectRepository extends JpaRepository<DonationProject, Integer> {
}
