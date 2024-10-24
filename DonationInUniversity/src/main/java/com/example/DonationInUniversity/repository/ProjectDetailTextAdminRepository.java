package com.example.DonationInUniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailTextAdminRepository extends JpaRepository<ProjectDetailTextAdmin,Integer>{
    @Query(value = "SELECT * FROM projectdetailtext where project_id=?1 and is_delete=1",nativeQuery = true)
    List<ProjectDetailTextAdmin> adminGetProjectDetailTextByProjectId(int id);
    @Modifying
    @Query(value = "DELETE FROM projectdetailtext WHERE project_id = ?1", nativeQuery = true)
    void deleteProjectDetailTextByProjectId(int id);
}
