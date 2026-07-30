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


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String invoiceNumber;


    private String vendorName;


    private Double amount;


    private LocalDate transactionDate;


    private String category;


    private String status;



    // Risk Analysis Fields

    private String riskLevel;



    private Integer riskScore;



    @Column(length = 500)
    private String riskReason;



    // Step 11 Explainable AI Fields

    @Column(length = 1000)
    private String riskFactors;



    @Column(length = 1000)
    private String aiRecommendation;





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



    public String getRiskFactors() {

        return riskFactors;
    }



    public void setRiskFactors(String riskFactors) {

        this.riskFactors = riskFactors;
    }



    public String getAiRecommendation() {

        return aiRecommendation;
    }



    public void setAiRecommendation(String aiRecommendation) {

        this.aiRecommendation = aiRecommendation;
    }

}