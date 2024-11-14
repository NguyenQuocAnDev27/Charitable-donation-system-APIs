package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.DonationProject;
import com.example.DonationInUniversity.model.TransferApplication;
import com.example.DonationInUniversity.model.User;
import com.example.DonationInUniversity.repository.TransferApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class TransferApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(TransferApplicationService.class);
    @Autowired
    TransferApplicationRepository transferApplicationRepository;

    public List<TransferApplication> getAllTransfer() {
        return transferApplicationRepository.findAll();
    }

    public TransferApplication save(TransferApplication transferApplication) {
        try {
            return transferApplicationRepository.save(transferApplication);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public int countTransferRequestsInCurrentWeek(int userId) {
        return transferApplicationRepository.countTransferRequestsInCurrentWeek(userId);
    }

    public Page<TransferApplication> getAllTransferApplicationByManager(int id, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        return this.transferApplicationRepository.findAllByManagerId(id, pageable);
    }

    public TransferApplication getTransferApplication(int id) {
        return transferApplicationRepository.findByProjectId(id);
    }

    public boolean existsByUserIdAndProjectId(User userId, DonationProject projectId) {
        return transferApplicationRepository.existsByUserIdAndProjectId(userId, projectId);
    }

    public Optional<TransferApplication> getTransferById(int id) {
        return transferApplicationRepository.findById(id);
    }
}
