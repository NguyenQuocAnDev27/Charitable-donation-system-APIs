package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProjectAdminRepository extends JpaRepository<DonationProject, Integer> {
    @Query(value = "SELECT * FROM donation_projects  where  is_deleted =?1", nativeQuery = true)
    Page<DonationProject> findProjectAdminByIsDeleted(int isDeleted,Pageable pageable);

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

    @Query("SELECT p.projectId, p.projectName, t.tagId, t.tagName, pt.projectTagId FROM DonationProject p " +
            "JOIN ProjectTag pt ON p.projectId = pt.donationProject.projectId " +
            "JOIN Tag t ON pt.tag.tagId = t.tagId " +
            "WHERE p.projectManager.userId = ?1 AND p.isDeleted = 1")
    List<Object[]> findAllProjectTagsByManager(int managerId);

    @Query("SELECT p FROM DonationProject p WHERE p.projectManager.userId = ?1 AND p.isDeleted = 1")
    List<DonationProject> findAllProjectsByManager(int managerId);

    @Query(value = "SELECT * FROM donation_projects WHERE project_manager_id = ?1 and is_deleted = 1", nativeQuery = true)
    Page<DonationProject> findAllByManagerId(int id, Pageable pageable);
    @Query("SELECT p FROM DonationProject p WHERE p.status= ?1 AND p.isDeleted = 1")
    List<DonationProject> findProjectStatus(String status);
    int countByIsDeleted(int isDeleted);
    @Query("SELECT COUNT(p) FROM DonationProject p WHERE " +
            "EXTRACT(MONTH FROM p.startDate) <= :month AND " +
            "EXTRACT(MONTH FROM p.endDate) >= :month AND " +
            "p.status = 'completed' AND p.isDeleted = 1")
    Long countCompletedProjectsByMonth(@Param("month") int month);

    @Query("SELECT COUNT(p) FROM DonationProject p WHERE " +
            "EXTRACT(MONTH FROM p.startDate) <= :month AND " +
            "EXTRACT(MONTH FROM p.endDate) >= :month AND " +
            "p.status = 'stopped' AND p.isDeleted = 1")
    Long countStoppedProjectsByMonth(@Param("month") int month);

    @Query("SELECT COUNT(p) FROM DonationProject p WHERE " +
            "EXTRACT(MONTH FROM p.startDate) <= :month AND " +
            "EXTRACT(MONTH FROM p.endDate) >= :month AND " +
            "p.status = 'pending' AND p.isDeleted = 1")
    Long countPendingProjectsByMonth(@Param("month") int month);
    @Query(value = "SELECT month, IFNULL(SUM(p.current_amount), 0) AS total_current_amount " +
            "FROM ( " +
            "  SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION " +
            "  SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) AS months " +
            "LEFT JOIN donation_projects p " +
            "  ON EXTRACT(MONTH FROM p.start_date) <= months.month " +
            "  AND (EXTRACT(MONTH FROM p.end_date) >= months.month OR p.end_date IS NULL) " +
            "  AND p.status IN ('pending', 'completed') " +
            "  AND p.is_deleted = 1 " +
            "GROUP BY months.month " +
            "ORDER BY months.month", nativeQuery = true)
    List<Object[]> countTotalCurrentAmountByMonth();
}