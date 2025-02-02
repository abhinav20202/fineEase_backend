package com.application.dto;

public class AdminDTO {
	
	private String Name;
	
	private String email;
	
	private Long phoneNo;
	
	private String password;
	
	private String confirmPassword;

	public AdminDTO(String name, String email, Long phoneNo, String password, String confirmPassword) {
		super();
		Name = name;
		this.email = email;
		this.phoneNo = phoneNo;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public AdminDTO() {
		super();
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	

}
