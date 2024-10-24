package com.example.DonationInUniversity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaction {

    @Id
    private String id;

    private String description;
    private String value;
    private String date;
    private String accountNo;
    private String refCode;
    private String balance;
    private String virtualAccountNo;
    private String virtualAccountName;
    private String contraAccountNo;
    private String contraAccountName;
    private String contraBankBin;
    private String contraBankName;
    private String paymentChannel;
    private Integer projectId;  // Additional field for project ID

    public Transaction() {
    }

    public Transaction(String id, String description, String value, String date, String accountNo, String refCode, String balance, String virtualAccountNo, String virtualAccountName, String contraAccountNo, String contraAccountName, String contraBankBin, String contraBankName, String paymentChannel, Integer projectId) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.date = date;
        this.accountNo = accountNo;
        this.refCode = refCode;
        this.balance = balance;
        this.virtualAccountNo = virtualAccountNo;
        this.virtualAccountName = virtualAccountName;
        this.contraAccountNo = contraAccountNo;
        this.contraAccountName = contraAccountName;
        this.contraBankBin = contraBankBin;
        this.contraBankName = contraBankName;
        this.paymentChannel = paymentChannel;
        this.projectId = projectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getVirtualAccountNo() {
        return virtualAccountNo;
    }

    public void setVirtualAccountNo(String virtualAccountNo) {
        this.virtualAccountNo = virtualAccountNo;
    }

    public String getVirtualAccountName() {
        return virtualAccountName;
    }

    public void setVirtualAccountName(String virtualAccountName) {
        this.virtualAccountName = virtualAccountName;
    }

    public String getContraAccountNo() {
        return contraAccountNo;
    }

    public void setContraAccountNo(String contraAccountNo) {
        this.contraAccountNo = contraAccountNo;
    }

    public String getContraAccountName() {
        return contraAccountName;
    }

    public void setContraAccountName(String contraAccountName) {
        this.contraAccountName = contraAccountName;
    }

    public String getContraBankBin() {
        return contraBankBin;
    }

    public void setContraBankBin(String contraBankBin) {
        this.contraBankBin = contraBankBin;
    }

    public String getContraBankName() {
        return contraBankName;
    }

    public void setContraBankName(String contraBankName) {
        this.contraBankName = contraBankName;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
