package com.example.DonationInUniversity.service.admin;

import com.example.DonationInUniversity.model.TransferApplication;
import com.example.DonationInUniversity.repository.TransferApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    public Optional<TransferApplication> getTransferById(int id) {
        return transferApplicationRepository.findById(id);
    }
}
