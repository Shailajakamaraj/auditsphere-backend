package com.auditsphere.auditspherebackend.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
public class DashboardResponseDTO {


    private Double totalAmount;


    private Long totalTransactions;


    private Long highRiskCount;


    private Long mediumRiskCount;


    private Long duplicateCount;



    private List<Object[]> riskSummary;


    private List<Object[]> categorySummary;


    private List<Object[]> monthlySummary;


    private List<Object[]> topVendors;


    private List<RecentTransactionDTO> recentTransactions;


}