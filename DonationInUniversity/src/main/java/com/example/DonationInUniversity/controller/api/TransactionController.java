package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.*;
import com.example.DonationInUniversity.repository.ProjectRepository;
import com.example.DonationInUniversity.repository.TransactionRepository;
import com.example.DonationInUniversity.repository.UserRepository;
import com.example.DonationInUniversity.service.api.ProjectService;
import com.example.DonationInUniversity.service.google.GoogleSheetsService;
import com.example.DonationInUniversity.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private static final String VIETQR_URL = "https://img.vietqr.io";
    @Value("${bank.id}")
    private String bankId;

    @Value("${account.no}")
    private String accountNo;
    private static final String TYPE_IMAGE = "print";

    @Autowired
    private GoogleSheetsService googleSheetsService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/create")
    public MyCustomResponse<RequestPaymentResponse> createRequestPayment(
            @RequestParam Integer amount, @RequestParam String message,
            @RequestParam String project_id, @RequestParam String user_id
    ) {
        try {
            // Retrieve the project by its ID
            DonationProject project = projectService.getProjectById(Integer.parseInt(project_id));

            if (project == null) {
                return new MyCustomResponse<>(404, "Project not found for id: " + project_id, null);
            }

            // Format the additional info
            String addInfo = Utils.formatAddInfo(user_id, project_id, message);

            // Rebuild the QR code URL dynamically using instance fields
            String createQrUrl = VIETQR_URL + "/image/" + bankId + "-" + accountNo + "-" + TYPE_IMAGE + ".png"
                    + "?amount=" + amount + "&addInfo=" + addInfo;

            // Fetch the QR code image from the URL
            URL url = new URL(createQrUrl);
            InputStream in = url.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            in.close();

            // Convert the image to Base64
            String qrContentBase64 = Base64.getEncoder().encodeToString(out.toByteArray());

            // Create the response with the project details and QR code
            RequestPaymentResponse paymentResponse = new RequestPaymentResponse(
                    project_id, project.getProjectName(), qrContentBase64
            );

            // Return a success response
            return new MyCustomResponse<>(200, "QR code generated successfully", paymentResponse);

        } catch (Exception e) {
            // Log and return an error response if something goes wrong
            logger.error("Error generating QR code", e);
            return new MyCustomResponse<>(500, "Failed to generate QR code", null);
        }
    }

    @GetMapping("/show_list")
    public MyCustomResponse<List<BankTransactionGGSheet>> getSheetsData() {
        List<BankTransactionGGSheet> transactions = new ArrayList<>();

        try {
            List<List<Object>> values = googleSheetsService.getSheetData();
            if (values == null || values.isEmpty()) {
                return new MyCustomResponse<>(HttpStatus.NO_CONTENT.value(), "No data found.", transactions);
            } else {
                for (List<Object> row : values) {
                    BankTransactionGGSheet transaction = new BankTransactionGGSheet();
                    if (row.size() > 0) transaction.setId(row.get(0).toString());
                    if (row.size() > 1) transaction.setDescription(row.get(1).toString());
                    if (row.size() > 2) transaction.setValue(row.get(2).toString());
                    if (row.size() > 3) transaction.setDate(row.get(3).toString());
                    if (row.size() > 4) transaction.setAccountNo(row.get(4).toString());
                    if (row.size() > 5) transaction.setRefCode(row.get(5).toString());
                    if (row.size() > 6) transaction.setBalance(row.get(6).toString());
                    if (row.size() > 7) transaction.setVirtualAccountNo(row.get(7).toString());
                    if (row.size() > 8) transaction.setVirtualAccountName(row.get(8).toString());
                    if (row.size() > 9) transaction.setContraAccountNo(row.get(9).toString());
                    if (row.size() > 10) transaction.setContraAccountName(row.get(10).toString());
                    if (row.size() > 11) transaction.setContraBankBin(row.get(11).toString());
                    if (row.size() > 12) transaction.setContraBankName(row.get(12).toString());
                    if (row.size() > 13) transaction.setPaymentChannel(row.get(13).toString());

                    transactions.add(transaction);
                }
                return new MyCustomResponse<>(HttpStatus.OK.value(), "Data fetched successfully.", transactions);
            }
        } catch (IOException e) {
            return new MyCustomResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error retrieving data.", null);
        }
    }

//    @GetMapping("/search")
//    public MyCustomResponse<Page<TransactionResponse>> searchTransactions(
//            @RequestParam(required = false) Long transactionId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        // Pageable logic to paginate the response
//        Pageable pageable = PageRequest.of(page, size);
//        Page<TransactionResponse> transactionResponses = transactionRepository
//                .findAllByOrderByDateDesc(pageable)
//                .map(this::mapToTransactionResponse);
//
//        return new MyCustomResponse<>(HttpStatus.OK.value(), "Transactions fetched successfully", transactionResponses);
//    }

    @GetMapping("/search")
    public MyCustomResponse<Page<TransactionResponse>> searchTransactions(
            @RequestParam(required = false) Long transactionId,
            @RequestParam(required = false) Integer projectId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        // Fetch transactions with combined criteria
        Page<Transaction> transactions = transactionRepository.searchTransactions(
                transactionId, projectId, startDate, endDate, pageable);

        // Map transactions to responses
        Page<TransactionResponse> transactionResponses = transactions.map(this::mapToTransactionResponse);

        return new MyCustomResponse<>(HttpStatus.OK.value(), "Transactions fetched successfully", transactionResponses);
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        // Extract user ID from the description using the helper method
        String userIdString = Utils.extractUserIdFromDescription(transaction.getDescription());

        // Find the user by the extracted user ID
        Optional<VerifiedUser> userOpt = userIdString != null ? userRepository.findByUserId(Integer.parseInt(userIdString)) : Optional.empty();
        String donorName = userOpt.map(VerifiedUser::getFullName).orElse("Unknown Donor");
        DonationProject project = projectService.getProjectById(transaction.getProjectId());
        String projectName = project.getProjectName();
        String projectId = project.getProjectId() + "";

        return new TransactionResponse(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getValue(),
                transaction.getDate(),
                donorName,
                projectName,
                projectId
        );
    }
}
