package com.application.dto;

import java.time.LocalDate;

public class DailyIncomeDTO {
	
   Double dailyIncome;
   LocalDate incomeDate;
   
	public DailyIncomeDTO(LocalDate date, Double dailyIncome) {
		super();
		this.incomeDate = date;
		this.dailyIncome = dailyIncome;
	}
	public LocalDate getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(LocalDate incomeDate) {
		this.incomeDate = incomeDate;
	}
	public Double getDailyIncome() {
		return dailyIncome;
	}
	public void setDailyIncome(Double dailyIncome) {
		this.dailyIncome = dailyIncome;
	}

	@Override
	public String toString() {
		return "DailyIncomeDTO [incomeDate=" + incomeDate + ", dailyExpense=" + dailyIncome + "]";
	}
}
