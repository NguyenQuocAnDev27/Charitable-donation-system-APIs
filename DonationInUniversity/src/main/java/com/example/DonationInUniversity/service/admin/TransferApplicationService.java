package com.example.DonationInUniversity.service.admin;


import com.example.DonationInUniversity.model.TransferApplication;
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

@Service
public class TransferApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(TransferApplicationService.class);
    @Autowired
    TransferApplicationRepository transferApplicationRepository;
    public TransferApplication save(TransferApplication transferApplication) {
        try {
            return transferApplicationRepository.save(transferApplication);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
    public int countTransferRequestsInCurrentWeek(int userId) {
        // Lấy ngày bắt đầu và kết thúc của tuần hiện tại
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // Gọi repository để đếm số lượng yêu cầu
        return transferApplicationRepository.countTransferRequestsInCurrentWeek(userId, startOfWeek, endOfWeek);
    }
    public Page<TransferApplication> getAllTransferApplicationByManager(int id, int pageNo) {
        Pageable pageable= PageRequest.of(pageNo-1, 5);
        return this.transferApplicationRepository.findAllByManagerId(id,pageable);
    }
}
