package com.auditsphere.auditspherebackend.dto;

import lombok.Data;

@Data
public class AuditInsightRequestDTO {

    private String invoiceNumber;

    private String vendorName;

    private Double amount;

    private String riskLevel;

    private Integer riskScore;

    private String riskReason;
}