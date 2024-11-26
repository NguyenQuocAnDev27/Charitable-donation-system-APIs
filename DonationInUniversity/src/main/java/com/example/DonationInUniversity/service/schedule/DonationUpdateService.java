package com.example.DonationInUniversity.service.schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class DonationUpdateService {

    @Autowired
    private EntityManager entityManager;

    @Scheduled(fixedRate = 300000) // 300000ms = 5 minutes
    @Transactional
    public void updateDonationAmounts() {
        String updateQuery = """
                UPDATE Donation_Projects dp
                JOIN (
                    SELECT
                        project_id,
                        SUM(CAST(REPLACE(value, '.', '') AS DECIMAL(15, 2))) AS total_donation
                    FROM Transaction
                    WHERE project_id IS NOT NULL
                    GROUP BY project_id
                ) t ON dp.project_id = t.project_id
                SET dp.current_amount = t.total_donation;
                """;

        try {
            Query query = entityManager.createNativeQuery(updateQuery);
            query.executeUpdate();
            System.out.println("Donation amounts updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating donation amounts: " + e.getMessage());
        }
    }
}
