package com.auditsphere.auditspherebackend.dto;

public class VendorSummaryDTO {

    private String vendorName;
    private double totalAmount;

    public VendorSummaryDTO() {
    }

    public VendorSummaryDTO(String vendorName, double totalAmount) {
        this.vendorName = vendorName;
        this.totalAmount = totalAmount;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}