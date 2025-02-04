package com.application.entity;




import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transactions {
	
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;
	
	private String Payee;
	
	private String payMode;		
	
	private String category;
	
	private Double amount;
	
	private String account;
	

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Users users;
	
	public Long getId() {
		return id;
	}
	public void setUsers(Users users) {
		this.users = users;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public String getCreatedDate() {
//		return createdDate;
//	}
//
//	public void setCreatedDate(String createdDate) {
//		this.createdDate = createdDate;
//	}

	public String getPayee() {
		return Payee;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public void setPayee(String payee) {
		Payee = payee;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Users getUsers() {
		return users;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

}
