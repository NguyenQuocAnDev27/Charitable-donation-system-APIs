package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.ProjectDetailText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailTextRepository extends JpaRepository<ProjectDetailText,Integer>{
    @Query(value = "SELECT * FROM projectdetailtext where project_id=?1 and is_deleted = 1",nativeQuery = true)
    List<ProjectDetailText> adminGetProjectDetailTextByProjectId(int id);
}
