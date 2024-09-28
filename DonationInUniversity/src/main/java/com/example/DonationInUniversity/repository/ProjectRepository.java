package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<DonationProject, Integer> {
}
