package com.auditsphere.auditspherebackend.dto;

public class RiskSummaryDTO {

    private String riskLevel;
    private long count;

    public RiskSummaryDTO() {
    }

    public RiskSummaryDTO(String riskLevel, long count) {
        this.riskLevel = riskLevel;
        this.count = count;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}