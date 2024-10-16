package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.ProjectDetailImageAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailImageAdminRepository extends JpaRepository<ProjectDetailImageAdmin,Integer>{
    @Query(value = "SELECT * FROM projectdetailimage where project_id=?1 and is_delete=1",nativeQuery = true)
    List<ProjectDetailImageAdmin> adminGetProjectDetailImageByProjectId(int id);
    @Modifying
    @Query(value = "DELETE FROM projectdetailimage WHERE project_id = ?1", nativeQuery = true)
    void deleteProjectDetailImageByProjectId(int id);
}