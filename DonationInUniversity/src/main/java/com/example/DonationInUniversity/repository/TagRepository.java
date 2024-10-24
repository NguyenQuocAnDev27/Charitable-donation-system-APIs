package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("SELECT pt.tag FROM ProjectTag pt JOIN pt.donationProject p WHERE p.projectManager.userId = :managerId")
    Page<Tag> findTagsByProjectManager(int managerId, Pageable pageable);
}
