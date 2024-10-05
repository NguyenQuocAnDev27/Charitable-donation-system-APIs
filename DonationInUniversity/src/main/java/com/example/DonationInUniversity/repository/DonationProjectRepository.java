package com.example.DonationInUniversity.repository;

import com.example.DonationInUniversity.model.DonationProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationProjectRepository extends PagingAndSortingRepository<DonationProject, Integer> {

}
