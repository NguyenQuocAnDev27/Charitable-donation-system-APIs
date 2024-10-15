package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.ProjectDetailText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectDetailTextRepository extends JpaRepository<ProjectDetailText, Integer> {
    @Query("SELECT i FROM ProjectDetailText i WHERE i.project.projectId = :projectId")
    List<ProjectDetailText> findByProjectId(Integer projectId);
}
