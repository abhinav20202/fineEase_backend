package com.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.dto.TransactionsDTO;
import com.application.service.TransactionsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired TransactionsService transactionsService;
	
	
	@GetMapping("/transactions")
	public String getTransactionsPage(Model model, HttpSession session) {
		model.addAttribute("transaction", new TransactionsDTO());
		Long userId = (Long) session.getAttribute("userId");
		
		if(userId == null) {
			return "redirect:/login";
		}
		
		List<TransactionsDTO> transactions = transactionsService.getTransactionsByUserId(userId);
		model.addAttribute("transactions", transactions);
		
		return "transactions";
	}
	
	@GetMapping("/reports")
	public String getReportingPage() {
		return "reporting";
	}
	
	/*
	 * @GetMapping("/settings") public String getSettingsPage() { return "settings";
	 * }
	 */
	
	@GetMapping("/logout")
	public String getLogoutPage() {
		return "login";
	}
	
//	@GetMapping("/admin")
//	public String getAdminPage() {
//		return "admin-dashboard";
//	}

}
