package com.example.DonationInUniversity.Repository;

import com.example.DonationInUniversity.Model.DonationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<DonationProject,Integer> {
    @Query("SELECT u FROM DonationProject u WHERE u.projectId = ?1")
    DonationProject findByProjectId(int id);
    @Query ("SELECT u FROM DonationProject  u where  u.projectManagerId =?1")
    DonationProject findByProjectManagerId(int id);
}
