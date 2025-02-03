package com.application.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;

    private Double monthlyIncome;
    private Double dailySpendLimit;
    private Double monthlySpendLimit;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
