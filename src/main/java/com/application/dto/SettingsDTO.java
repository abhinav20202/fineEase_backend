package com.application.dto;

public class SettingsDTO {
    private Long userId;
    private Double monthlyIncome;
    private Double dailySpendLimit;
    private Double monthlySpendLimit;

    public SettingsDTO(Long userId, Double monthlyIncome, Double dailySpendLimit, Double monthlySpendLimit) {
        this.userId = userId;
        this.monthlyIncome = monthlyIncome;
        this.dailySpendLimit = dailySpendLimit;
        this.monthlySpendLimit = monthlySpendLimit;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Double getDailySpendLimit() {
        return dailySpendLimit;
    }

    public void setDailySpendLimit(Double dailySpendLimit) {
        this.dailySpendLimit = dailySpendLimit;
    }

    public Double getMonthlySpendLimit() {
        return monthlySpendLimit;
    }

    public void setMonthlySpendLimit(Double monthlySpendLimit) {
        this.monthlySpendLimit = monthlySpendLimit;
    }
}
