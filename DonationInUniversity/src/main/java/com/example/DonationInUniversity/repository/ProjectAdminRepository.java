package com.example.DonationInUniversity.Repository;
import com.example.DonationInUniversity.model.DonationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProjectAdminRepository extends JpaRepository<DonationProject,Integer> {
    @Query(value = "SELECT * FROM donation_projects   where  is_deleted =?1",nativeQuery = true)
    List<DonationProject> findProjectAdminByIsDeleted(int isDeleted);
    @Query ("SELECT u FROM DonationProject  u where  u.projectManager =?1")
    DonationProject findProjectAdminByProjectManagerId(int id);
    @Query(value = "SELECT * FROM donation_projects where project_manager_id=?1 and is_deleted = 1",nativeQuery = true)
    List<DonationProject> adminGetDonationProjectByManager(int id);
}