package com.auditsphere.auditspherebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter

public class Transaction {
    @Column
    private Integer riskScore;

    @Column(length = 500)
    private String riskReason;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNumber;

    private String vendorName;

    private Double amount;

    private LocalDate transactionDate;

    private String category;

    private String status;

    private String riskLevel;


    // Default constructor required by JPA
    public Transaction() {

    }
    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }


    public String getRiskReason() {
        return riskReason;
    }

    public void setRiskReason(String riskReason) {
        this.riskReason = riskReason;
    }
}