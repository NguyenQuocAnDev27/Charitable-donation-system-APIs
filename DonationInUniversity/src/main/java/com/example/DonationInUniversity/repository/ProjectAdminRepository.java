package com.example.DonationInUniversity.repository;
import com.example.DonationInUniversity.model.DonationProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProjectAdminRepository extends JpaRepository<DonationProject,Integer> {
    @Query(value = "SELECT * FROM donation_projects  where  is_deleted =?1",nativeQuery = true)
    Page<DonationProject> findProjectAdminByIsDeleted(int isDeleted, Pageable pageable);
    @Query(value = "SELECT * FROM donation_projects where project_manager_id=?1 and is_deleted = 1",nativeQuery = true)
    List<DonationProject> adminGetDonationProjectByManager(int id);
    @Query(value = "SELECT * FROM donation_projects where project_id=?1 and is_deleted = 1",nativeQuery = true)
    DonationProject getDonationProjectByProjectId(int id);
}