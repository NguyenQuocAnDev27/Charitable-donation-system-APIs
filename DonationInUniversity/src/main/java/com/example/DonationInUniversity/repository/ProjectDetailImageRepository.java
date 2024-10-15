package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.ProjectDetailImage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectDetailImageRepository extends JpaRepository<ProjectDetailImage, Integer> {
    @Query("SELECT i FROM ProjectDetailImage i WHERE i.project.projectId = :projectId")
    List<ProjectDetailImage> findByProjectId(@Param("projectId") Integer projectId);
}
