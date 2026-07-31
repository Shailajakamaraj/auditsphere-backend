package com.auditsphere.auditspherebackend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetailsDTO {

    private Long id;

    private String invoiceNumber;

    private String vendorName;

    private BigDecimal amount;

    private LocalDate transactionDate;

    private String category;

    private String status;

    private String riskLevel;

    private Integer riskScore;

    private String riskReason;

    private String riskFactors;

    private String aiRecommendation;

}