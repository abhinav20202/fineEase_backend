package com.application.entity;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="users")
public class Users {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "user_id", nullable = false, updatable = false)
	private Long userId;
	
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = false)
    private String password;
	
	// @Column(nullable = false, unique = false)
	//private LocalDateTime createdAt;
	
    @Column(nullable = false)
    private String role; 
    
    @Column(nullable = true) // Prevents updates after user creation
    private LocalDate registrationDate;
	
    @Transient
    private String confirmPassword;
    
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transactions> transactions;
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Transactions> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}
    
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Long getUser_id() {
		return userId;
	}
	public void setUser_id(Long user_id) {
		this.userId = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Users [user_id=" + userId + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + ", transactions=" + transactions + "]";
	}
	
	
	
	}
