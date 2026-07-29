package com.auditsphere.auditspherebackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DashboardResponseDTO {

    private Double totalAmount;

    private Long totalTransactions;

    private Long highRiskCount;

    private List<Object[]> riskSummary;

    private List<Object[]> categorySummary;

    private List<Object[]> monthlySummary;

    private List<Object[]> topVendors;

}