package com.example.DonationInUniversity.Repository;

import com.example.DonationInUniversity.model.DonationProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<DonationProject,Integer> {
    @Query (value = "SELECT * FROM donation_projects   where  is_deleted =?1",nativeQuery = true)
    List<DonationProject> findByIsDeleted(int isDeleted);
    @Query("SELECT u FROM DonationProject u WHERE u.projectId = ?1")
    DonationProject findByProjectId(int id);
    @Query ("SELECT u FROM DonationProject  u where  u.projectManagerId =?1")
    DonationProject findByProjectManagerId(int id);

}
