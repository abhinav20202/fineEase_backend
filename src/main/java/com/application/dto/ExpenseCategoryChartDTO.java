package com.application.dto;

public class ExpenseCategoryChartDTO {
	
	 private String expenseCategory;
	    private Double categoryExpense;

	    public ExpenseCategoryChartDTO(String expenseCategory, Double categoryExpense) {
	        this.expenseCategory = expenseCategory;
	        this.categoryExpense = categoryExpense;
	    }

	    public String getExpenseCategory() {
	        return expenseCategory;
	    }

	    public void setExpenseCategory(String expenseCategory) {
			this.expenseCategory = expenseCategory;
		}

		public void setCategoryExpense(Double categoryExpense) {
			this.categoryExpense = categoryExpense;
		}

		public Double getCategoryExpense() {
	        return categoryExpense;
	    }

		@Override
		public String toString() {
			return "ExpenseCategoryChartDTO [expenseCategory=" + expenseCategory + ", categoryExpense="
					+ categoryExpense + "]";
		}

		
}
