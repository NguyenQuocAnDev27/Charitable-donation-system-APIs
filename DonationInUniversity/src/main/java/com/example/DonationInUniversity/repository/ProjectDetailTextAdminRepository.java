package com.example.DonationInUniversity.repository;


import com.example.DonationInUniversity.model.ProjectDetailText;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailTextAdminRepository extends JpaRepository<ProjectDetailText, Integer> {
    @Query(value = "SELECT p FROM ProjectDetailText p where p.project.projectId=?1 and p.isDelete=1")
    List<ProjectDetailText> adminGetProjectDetailTextByProjectId(int id);

    @Modifying
    @Query(value = "DELETE FROM ProjectDetailText p WHERE p.project.projectId = ?1")
    void deleteProjectDetailTextByProjectId(int id);
}
