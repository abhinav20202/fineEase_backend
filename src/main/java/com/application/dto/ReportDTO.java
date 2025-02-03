package com.application.dto;

public class ReportDTO {
	private String category;
	
	private Double totalSpent;
	
	private Double highestSpent;
	
	private Double LowestSpent;
	
	private Double averageSpent;

	public ReportDTO() {
		super();
	}

	public ReportDTO(String category, Double totalSpent, Double highestSpent, Double lowestSpent, Double averageSpent) {
		super();
		this.category = category;
		this.totalSpent = totalSpent;
		this.highestSpent = highestSpent;
		LowestSpent = lowestSpent;
		this.averageSpent = averageSpent;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(Double totalSpent) {
		this.totalSpent = totalSpent;
	}

	public Double getHighestSpent() {
		return highestSpent;
	}

	public void setHighestSpent(Double highestSpent) {
		this.highestSpent = highestSpent;
	}

	public Double getLowestSpent() {
		return LowestSpent;
	}

	public void setLowestSpent(Double lowestSpent) {
		LowestSpent = lowestSpent;
	}

	public Double getAverageSpent() {
		return averageSpent;
	}

	public void setAverageSpent(Double averageSpent) {
		this.averageSpent = averageSpent;
	}

}
