package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.Role;
import com.example.DonationInUniversity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAdminRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM users   where  is_deleted =?1",nativeQuery = true)
    Page<User> findUsersByIsDeleted(int isDeleted, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    List<User> findByRole(Role role);
}
