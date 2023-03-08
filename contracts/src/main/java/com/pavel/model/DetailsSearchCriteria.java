package com.pavel.model;

public class DetailsSearchCriteria {
    private double minPrice;
    private double maxPrice;
    private int minMinCpm;
    private int maxMinCpm;

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinMinCpm() {
        return minMinCpm;
    }

    public void setMinMinCpm(int minMinCpm) {
        this.minMinCpm = minMinCpm;
    }

    public int getMaxMinCpm() {
        return maxMinCpm;
    }

    public void setMaxMinCpm(int maxMinCpm) {
        this.maxMinCpm = maxMinCpm;
    }
}
