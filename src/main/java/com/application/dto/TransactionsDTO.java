package com.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TransactionsDTO {
	
	public TransactionsDTO(LocalDate createdDateTime, String category, String payee, String payMode, Long userId,
			String account, Long id) {
		super();
		this.createdDateTime = createdDateTime;
		this.category = category;
		this.payee = payee;
		this.payMode = payMode;
		this.userId = userId;
		this.account = account;
		this.id = id;
	}

	public TransactionsDTO() {
		super();
	}
  
    private Long id;
	

	private LocalDate createdDateTime;
	
	private String category;
	
	private String payee;
	
	private String payMode;
	
	private double amount; 
	
	private Long userId;
	
	private String account;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LocalDate getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDate createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

// public LocalDate getParsedCreatedDate() {
//	 LocalDate date = LocalDate.parse(this.createdDateTime);
//
//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//	return date.format(formatter);
// }
}
