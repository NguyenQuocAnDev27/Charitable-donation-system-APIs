package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.ProjectTag;
import com.example.DonationInUniversity.model.Tag;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, Integer> {
    @Query("SELECT pt.tag FROM ProjectTag pt JOIN pt.donationProject p WHERE p.projectManager.userId = :managerId")
    Page<Tag> findTagsByProjectManager(@Param("managerId") int managerId, Pageable pageable);

    @Query("SELECT pt.tag FROM ProjectTag pt WHERE pt.donationProject.projectId = :projectId")
    List<Tag> findTagsByProjectId(@Param("projectId") Integer projectId);

}