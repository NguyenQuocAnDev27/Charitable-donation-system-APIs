package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.BankTransactionGGSheet;
import com.example.DonationInUniversity.model.MyCustomResponse;
import com.example.DonationInUniversity.service.google.GoogleSheetsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private static final String SHEET_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vQj-64oKGb1IkoFweDjBobXBHApEpDK_sz7eSdzue37B7SMVnJ0oT4XlUYaicjedGI2z_keTzfjlsFw/pubhtml";

    @Autowired
    private GoogleSheetsService googleSheetsService;

    @GetMapping("/sheets")
    public List<BankTransactionGGSheet> convertSheetToJson() {
        List<BankTransactionGGSheet> transactions = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(SHEET_URL).get();
            Element table = doc.selectFirst("table.waffle");

            if (table != null) {
                Elements rows = table.select("tr");
                for (int i = 2; i < rows.size(); i++) { // Start from the third row
                    Elements cells = rows.get(i).select("td");

                    BankTransactionGGSheet transaction = new BankTransactionGGSheet();
                    transaction.setId(cells.get(0).text().trim());
                    transaction.setDescription(cells.get(1).text().trim());
                    transaction.setValue(cells.get(2).text().trim());
                    transaction.setDate(cells.get(3).text().trim());
                    transaction.setAccountNo(cells.get(4).text().trim());
                    transaction.setRefCode(cells.get(5).text().trim());
                    transaction.setBalance(cells.get(6).text().trim());
                    transaction.setVirtualAccountNo(cells.get(7).text().trim());
                    transaction.setVirtualAccountName(cells.get(8).text().trim());
                    transaction.setContraAccountNo(cells.get(9).text().trim());
                    transaction.setContraAccountName(cells.get(10).text().trim());
                    transaction.setContraBankBin(cells.get(11).text().trim());
                    transaction.setContraBankName(cells.get(12).text().trim());
                    transaction.setPaymentChannel(cells.get(14).text().trim());

                    transactions.add(transaction);
                }
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return transactions;
    }

    @GetMapping("/data")
    public Map<String, Object> getSheetData() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<List<Object>> values = googleSheetsService.getSheetData();
            if (values == null || values.isEmpty()) {
                response.put("message", "No data found.");
                response.put("data", null);
            } else {
                response.put("message", "Data fetched successfully.");
                response.put("data", values);
            }
        } catch (IOException e) {
            response.put("message", "Error retrieving data.");
            response.put("error", e.getMessage());
        }

        return response;
    }

    @GetMapping("/v2/data")
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
}
