package com.auditsphere.auditspherebackend.dto;

public class MonthlySummaryDTO {

    private String month;

    private Long count;

    public MonthlySummaryDTO() {
    }

    public MonthlySummaryDTO(String month, Long count) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}