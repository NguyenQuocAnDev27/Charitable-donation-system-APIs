package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.ProjectDetailImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailImageRepository extends JpaRepository<ProjectDetailImage,Integer>{
    @Query(value = "SELECT * FROM projectdetailiamge where project_id=?1 and is_deleted = 1",nativeQuery = true)
    List<ProjectDetailImage> adminGetProjectDetailImageByProjectId(int id);
}
