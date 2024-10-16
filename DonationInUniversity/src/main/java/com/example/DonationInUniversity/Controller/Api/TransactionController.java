package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.BankTransactionGGSheet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    private static final String SHEET_URL = "https://docs.google.com/spreadsheets/d/e/2PACX-1vQj-64oKGb1IkoFweDjBobXBHApEpDK_sz7eSdzue37B7SMVnJ0oT4XlUYaicjedGI2z_keTzfjlsFw/pubhtml";

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
                    transaction.setMaGD(cells.get(0).text().trim());
                    transaction.setMoTa(cells.get(1).text().trim());
                    transaction.setGiaTri(cells.get(2).text().trim());
                    transaction.setNgayDienRa(cells.get(3).text().trim());
                    transaction.setSoTaiKhoan(cells.get(4).text().trim());
                    transaction.setMaThamChieu(cells.get(5).text().trim());
                    transaction.setSoDu(cells.get(6).text().trim());
                    transaction.setSoTaiKhoanAo(cells.get(7).text().trim());
                    transaction.setTenTaiKhoanAo(cells.get(8).text().trim());
                    transaction.setSoTaiKhoanDoiUng(cells.get(9).text().trim());
                    transaction.setTenTaiKhoanDoiUng(cells.get(10).text().trim());
                    transaction.setMaBinNganHangDoiUng(cells.get(11).text().trim());
                    transaction.setTenNHTaiKhoanDoiUng(cells.get(12).text().trim());
                    transaction.setKenhThanhToan(cells.get(14).text().trim());

                    transactions.add(transaction);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
