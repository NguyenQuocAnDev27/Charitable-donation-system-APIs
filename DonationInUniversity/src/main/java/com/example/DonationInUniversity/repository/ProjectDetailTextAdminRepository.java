package com.example.DonationInUniversity.repository;


import com.example.DonationInUniversity.model.ProjectDetailText;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailTextAdminRepository extends JpaRepository<ProjectDetailText, Integer> {
    @Query(value = "SELECT * FROM project_detail_text where project_id=?1 and is_delete=1", nativeQuery = true)
    List<ProjectDetailText> adminGetProjectDetailTextByProjectId(int id);

    @Modifying
    @Query(value = "DELETE FROM project_detail_text WHERE project_id = ?1", nativeQuery = true)
    void deleteProjectDetailTextByProjectId(int id);
}
