//package com.example.DonationInUniversity.service.schedule;
//
//import com.example.DonationInUniversity.model.Transaction;
//import com.example.DonationInUniversity.repository.TransactionRepository;
//import com.example.DonationInUniversity.service.google.GoogleSheetsService;
//import jakarta.transaction.Transactional;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import java.io.IOException;
//import java.util.List;
//
//import static com.example.DonationInUniversity.utils.Utils.extractProjectId;
//
//@Service
//public class ScheduledTransactionService {
//    private static final Logger logger = LoggerFactory.getLogger(ScheduledTransactionService.class);
//
//    @Autowired
//    private GoogleSheetsService googleSheetsService;
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Scheduled(fixedRate = 300000)  // Every 5 minutes = 300000
//    @Transactional
//    public void fetchAndSaveTransactions() {
//        try {
//            // Call Google Sheets API to get data
//            List<List<Object>> sheetData = googleSheetsService.getSheetData();
//
//            if (sheetData != null && !sheetData.isEmpty()) {
//                for (List<Object> row : sheetData) {
//                    // Create a new Transaction entity
//                    Transaction transaction = new Transaction();
//
//                    if (row.size() > 0) transaction.setId(row.get(0).toString());
//                    if (row.size() > 1) transaction.setDescription(row.get(1).toString());
//                    if (row.size() > 2) transaction.setValue(row.get(2).toString());
//                    if (row.size() > 3) transaction.setDate(row.get(3).toString());
//                    if (row.size() > 4) transaction.setAccountNo(row.get(4).toString());
//                    if (row.size() > 5) transaction.setRefCode(row.get(5).toString());
//                    if (row.size() > 6) transaction.setBalance(row.get(6).toString());
//                    if (row.size() > 7) transaction.setVirtualAccountNo(row.get(7).toString());
//                    if (row.size() > 8) transaction.setVirtualAccountName(row.get(8).toString());
//                    if (row.size() > 9) transaction.setContraAccountNo(row.get(9).toString());
//                    if (row.size() > 10) transaction.setContraAccountName(row.get(10).toString());
//                    if (row.size() > 11) transaction.setContraBankBin(row.get(11).toString());
//                    if (row.size() > 12) transaction.setContraBankName(row.get(12).toString());
//                    if (row.size() > 13) transaction.setPaymentChannel(row.get(13).toString());
//
//                    // Extract mã chiến dịch (project ID) from Description and set it in projectId
//                    String description = transaction.getDescription();
//
//                    // Check if the description matches the required format
//                    if (description != null && description.contains("SGUC") && description.contains("USR")) {
//                        Integer projectId = extractProjectId(description);
//                        transaction.setProjectId(projectId);
//
//                        // Check if the transaction with the same Id already exists
//                        if (!transactionRepository.existsById(transaction.getId())) {
//                            // If the transaction is new, save it
//                            transactionRepository.save(transaction);
//                        } else {
//                            // Log or handle case where transaction already exists
//                            logger.info("Transaction with ID {} already exists, skipping insertion.", transaction.getId());
//                        }
//                    } else {
//                        // Log or handle case where description doesn't match the expected format
//                        logger.info("Transaction with ID {} skipped due to invalid description format: {}", transaction.getId(), description);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            logger.error("Error fetching data from Google Sheets: {}", e.getMessage());
//            e.printStackTrace();  // Handle exceptions properly in production
//        }
//    }
//}
