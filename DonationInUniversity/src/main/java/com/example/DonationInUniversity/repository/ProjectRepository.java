package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<DonationProject,Integer> {
//    @Query (value = "SELECT * FROM donation_projects   where  is_deleted =?1",nativeQuery = true)
//    List<DonationProject> findByIsDeleted(int isDeleted);
//    @Query("SELECT u FROM DonationProject u WHERE u.projectId = ?1")
//    DonationProject findByProjectId(int id);
//    @Query ("SELECT u FROM DonationProject  u where  u.projectManagerId =?1")
//    DonationProject findByProjectManagerId(int id);

    Page<DonationProject> findByProjectNameContainingIgnoreCase(String query, Pageable pageable);

    @Query("SELECT p FROM DonationProject p WHERE p.projectManager.userId = :projectManagerId " +
            "AND (:searchString IS NULL OR p.projectName LIKE %:searchString%)")
    Page<DonationProject> findByProjectManagerAndSearchString(
            @Param("projectManagerId") int projectManagerId,
            @Param("searchString") String searchString,
            Pageable pageable);
}
