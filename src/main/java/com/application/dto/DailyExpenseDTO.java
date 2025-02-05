package com.application.dto;

import java.time.LocalDate;

public class DailyExpenseDTO {
	
	LocalDate expenseDate;
	Double dailyExpense;
	
	public DailyExpenseDTO(LocalDate expenseDate, Double dailyExpense) {
		super();
		this.expenseDate = expenseDate;
		this.dailyExpense = dailyExpense;
	}
	public LocalDate getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}
	public Double getDailyExpense() {
		return dailyExpense;
	}
	public void setDailyExpense(Double dailyExpense) {
		this.dailyExpense = dailyExpense;
	}

	@Override
	public String toString() {
		return "DailyExpenseDTO [expenseDate=" + expenseDate + ", dailyExpense=" + dailyExpense + "]";
	}
}
