package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<DonationProject,Integer> {
    Page<DonationProject> findByProjectNameContainingIgnoreCase(String query, Pageable pageable);

    @Query("SELECT p FROM DonationProject p WHERE p.projectManager.userId = :projectManagerId " +
            "AND (:searchString IS NULL OR p.projectName LIKE %:searchString%)")
    Page<DonationProject> findByProjectManagerAndSearchString(
            @Param("projectManagerId") int projectManagerId,
            @Param("searchString") String searchString,
            Pageable pageable);

    @Query("SELECT p.projectId, p.projectName, t.tagId, t.tagName " +
            "FROM DonationProject p " +
            "JOIN ProjectTag pt ON p.projectId = pt.donationProject.projectId " +
            "JOIN Tag t ON pt.tag.tagId = t.tagId " +
            "WHERE p.projectManager.userId = :managerId " +
            "AND p.isDeleted = 0")
    List<Object[]> findAllProjectTagsByManager(int managerId);


}
