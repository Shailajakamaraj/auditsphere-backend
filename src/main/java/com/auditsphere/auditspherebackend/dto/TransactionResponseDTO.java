package com.auditsphere.auditspherebackend.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class TransactionResponseDTO {


    private Long id;


    private String invoiceNumber;


    private String vendorName;


    private Double amount;


    private LocalDate transactionDate;


    private String category;


    private String status;


    // Risk Analysis

    private String riskLevel;


    private Integer riskScore;


    private String riskReason;



    // Step 11 Explainable AI Fields

    private String riskFactors;


    private String aiRecommendation;





    // Default constructor

    public TransactionResponseDTO() {

    }





    public TransactionResponseDTO(
            Long id,
            String invoiceNumber,
            String vendorName,
            Double amount,
            LocalDate transactionDate,
            String category,
            String status,
            String riskLevel
    ) {


        this.id = id;

        this.invoiceNumber = invoiceNumber;

        this.vendorName = vendorName;

        this.amount = amount;

        this.transactionDate = transactionDate;

        this.category = category;

        this.status = status;

        this.riskLevel = riskLevel;

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