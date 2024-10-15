package com.example.DonationInUniversity.repository;
import com.example.DonationInUniversity.model.DonationProject;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonationProjectRepository extends PagingAndSortingRepository<DonationProject, Integer> {

}