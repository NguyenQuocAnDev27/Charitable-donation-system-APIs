package com.example.DonationInUniversity.Repository;

import com.example.DonationInUniversity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Custom query using @Query annotation
    @Query("SELECT u FROM User u WHERE u.roleId = ?1")
    List<User> findUsersByRoleId(int roleId);
}
