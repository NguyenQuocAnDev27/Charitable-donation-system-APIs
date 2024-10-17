package com.example.DonationInUniversity.model;

public class BankTransactionGGSheet {
    private String Id;               // Mã GD (Transaction ID)
    private String Description;               // Mô tả (Description)
    private String Value;             // Giá trị (Value)
    private String Date;         // Ngày diễn ra (Date)
    private String AccountNo;         // Số tài khoản (Account Number)
    private String RefCode;        // Mã tham chiếu (Reference Code)
    private String Balance;               // Số dư (Balance)
    private String VirtualAccountNo;       // Số tài khoản ảo (Virtual Account Number)
    private String VirtualAccountName;      // Tên tài khoản ảo (Virtual Account Name)
    private String ContraAccountNo;   // Số tài khoản đối ứng (Counter Account Number)
    private String ContraAccountName;  // Tên tài khoản đối ứng (Counter Account Name)
    private String ContraBankBin; // Mã BIN ngân hàng đối ứng (Counter Bank BIN)
    private String ContraBankName; // Tên NH tài khoản đối ứng (Counter Bank Name)
    private String PaymentChannel;      // Kênh thanh toán (Payment Channel)

    public BankTransactionGGSheet() {
    }

    public BankTransactionGGSheet(String id, String description, String value, String date, String accountNo, String refCode, String balance, String virtualAccountNo, String virtualAccountName, String contraAccountNo, String contraAccountName, String contraBankBin, String contraBankName, String paymentChannel) {
        Id = id;
        Description = description;
        Value = value;
        Date = date;
        AccountNo = accountNo;
        RefCode = refCode;
        Balance = balance;
        VirtualAccountNo = virtualAccountNo;
        VirtualAccountName = virtualAccountName;
        ContraAccountNo = contraAccountNo;
        ContraAccountName = contraAccountName;
        ContraBankBin = contraBankBin;
        ContraBankName = contraBankName;
        PaymentChannel = paymentChannel;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(String accountNo) {
        AccountNo = accountNo;
    }

    public String getRefCode() {
        return RefCode;
    }

    public void setRefCode(String refCode) {
        RefCode = refCode;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getVirtualAccountNo() {
        return VirtualAccountNo;
    }

    public void setVirtualAccountNo(String virtualAccountNo) {
        VirtualAccountNo = virtualAccountNo;
    }

    public String getVirtualAccountName() {
        return VirtualAccountName;
    }

    public void setVirtualAccountName(String virtualAccountName) {
        VirtualAccountName = virtualAccountName;
    }

    public String getContraAccountNo() {
        return ContraAccountNo;
    }

    public void setContraAccountNo(String contraAccountNo) {
        ContraAccountNo = contraAccountNo;
    }

    public String getContraAccountName() {
        return ContraAccountName;
    }

    public void setContraAccountName(String contraAccountName) {
        ContraAccountName = contraAccountName;
    }

    public String getContraBankBin() {
        return ContraBankBin;
    }

    public void setContraBankBin(String contraBankBin) {
        ContraBankBin = contraBankBin;
    }

    public String getContraBankName() {
        return ContraBankName;
    }

    public void setContraBankName(String contraBankName) {
        ContraBankName = contraBankName;
    }

    public String getPaymentChannel() {
        return PaymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        PaymentChannel = paymentChannel;
    }
}
