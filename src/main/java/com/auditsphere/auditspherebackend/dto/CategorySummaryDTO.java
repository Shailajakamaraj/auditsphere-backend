package com.auditsphere.auditspherebackend.dto;

public class CategorySummaryDTO {

    private String category;
    private long count;

    public CategorySummaryDTO() {
    }

    public CategorySummaryDTO(String category, long count) {
        this.category = category;
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}