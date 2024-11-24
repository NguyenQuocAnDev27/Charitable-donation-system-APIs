package com.example.DonationInUniversity.repository;


import com.example.DonationInUniversity.model.ProjectDetailImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailImageAdminRepository extends JpaRepository<ProjectDetailImage, Integer> {
    @Query(value = "SELECT p FROM ProjectDetailImage p where p.project.projectId=?1 and p.isDelete=1")
    List<ProjectDetailImage> adminGetProjectDetailImageByProjectId(int id);

    @Modifying
    @Query(value = "DELETE FROM ProjectDetailImage p WHERE p.project.projectId = ?1")
    void deleteProjectDetailImageByProjectId(int id);
}