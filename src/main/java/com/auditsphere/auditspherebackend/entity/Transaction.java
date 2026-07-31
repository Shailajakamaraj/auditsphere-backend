package com.auditsphere.auditspherebackend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;



@Entity
@Table(name="transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(nullable=false, unique=true)
    private String invoiceNumber;



    @Column(nullable=false)
    private String vendorName;



    @Column(nullable=false, precision=12, scale=2)
    private BigDecimal amount;



    private LocalDate transactionDate;



    private String category;



    @Enumerated(EnumType.STRING)
    private TransactionStatus status;



    // ============================
    // AI Risk Analysis
    // ============================


    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;



    private Integer riskScore;



    @Column(length=500)
    private String riskReason;



    @Column(length=1000)
    private String riskFactors;



    @Column(length=1000)
    private String aiRecommendation;



}