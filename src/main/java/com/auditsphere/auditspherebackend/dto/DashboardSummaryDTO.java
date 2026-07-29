package com.auditsphere.auditspherebackend.dto;

public class DashboardSummaryDTO {

    private long totalTransactions;
    private double totalAmount;
    private long highRiskTransactions;
    private long duplicateTransactions;

    public DashboardSummaryDTO() {
    }

    public DashboardSummaryDTO(long totalTransactions,
                               double totalAmount,
                               long highRiskTransactions,
                               long duplicateTransactions) {
        this.totalTransactions = totalTransactions;
        this.totalAmount = totalAmount;
        this.highRiskTransactions = highRiskTransactions;
        this.duplicateTransactions = duplicateTransactions;
    }

    public long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getHighRiskTransactions() {
        return highRiskTransactions;
    }

    public void setHighRiskTransactions(long highRiskTransactions) {
        this.highRiskTransactions = highRiskTransactions;
    }

    public long getDuplicateTransactions() {
        return duplicateTransactions;
    }

    public void setDuplicateTransactions(long duplicateTransactions) {
        this.duplicateTransactions = duplicateTransactions;
    }
}