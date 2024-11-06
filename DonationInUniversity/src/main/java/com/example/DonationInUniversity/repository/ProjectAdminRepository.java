package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectAdminRepository extends JpaRepository<DonationProject, Integer> {
    @Query(value = "SELECT * FROM donation_projects  where  is_deleted =?1", nativeQuery = true)
    List<DonationProject> findProjectAdminByIsDeleted(int isDeleted);

    @Query("SELECT u FROM DonationProject  u where  u.projectManager =?1")
    DonationProject findProjectAdminByProjectManagerId(int id);

    @Query(value = "SELECT * FROM donation_projects where project_manager_id=?1 and is_deleted = 1", nativeQuery = true)
    List<DonationProject> adminGetDonationProjectByManager(int id);

    @Query(value = "SELECT * FROM donation_projects where project_id=?1 and is_deleted = 1", nativeQuery = true)
    DonationProject getDonationProjectByProjectId(int id);

    @Query("SELECT p, t FROM DonationProject p " +
            "JOIN ProjectTag pt ON p.projectId = pt.donationProject.projectId " +
            "JOIN Tag t ON pt.tag.tagId = t.tagId " +
            "WHERE p.isDeleted = 1")
    List<Object[]> findAllProjectTags();

    @Query("SELECT p.projectId, p.projectName, t.tagId, t.tagName FROM DonationProject p " +
            "JOIN ProjectTag pt ON p.projectId = pt.donationProject.projectId " +
            "JOIN Tag t ON pt.tag.tagId = t.tagId " +
            "WHERE p.projectManager.userId = ?1 AND p.isDeleted = 1")
    List<Object[]> findAllProjectTagsByManager(int managerId);

    @Query("SELECT p FROM DonationProject p WHERE p.projectManager.userId = ?1 AND p.isDeleted = 1")
    List<DonationProject> findAllProjectsByManager(int managerId);

    @Query(value = "SELECT * FROM donation_projects WHERE project_manager_id = ?1", nativeQuery = true)
    Page<DonationProject> findAllByManagerId(int id, Pageable pageable);

}