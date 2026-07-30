package com.auditsphere.auditspherebackend.dto;

import java.time.LocalDate;

public class RecentTransactionDTO {

    private String invoiceNumber;

    private String vendorName;

    private double amount;

    private String riskLevel;

    private LocalDate transactionDate;

    private String status;


    // Default constructor required
    public RecentTransactionDTO() {
    }


    // Parameterized constructor
    public RecentTransactionDTO(String invoiceNumber,
                                String vendorName,
                                double amount,
                                String riskLevel,
                                LocalDate transactionDate,
                                String status) {

        this.invoiceNumber = invoiceNumber;
        this.vendorName = vendorName;
        this.amount = amount;
        this.riskLevel = riskLevel;
        this.transactionDate = transactionDate;
        this.status = status;
    }


    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }


    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }


    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}