package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.application.dto.TransactionsDTO;
import com.application.entity.Transactions;
import com.application.entity.Users;
import com.application.service.TransactionsService;
import com.application.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TransactionsController {
	
	@Autowired UserService service;
	@Autowired TransactionsService transactionsService;
	
	
	@GetMapping("/transactions/edit/{id}")
	public String editTransaction(@PathVariable Long id, Model model) {
		Transactions transactions = transactionsService.getTransactionById(id);
		if(transactions == null) {
			return "redirect:/transactions";
		}
		model.addAttribute("transaction", transactions);
		return "transactions";
		
	}
	
	@PostMapping("/transactions/add")
	public String saveOrUpdateTransactionsData(@ModelAttribute("transaction") TransactionsDTO transactionsDTO, HttpSession session, Model model) {

		Long userId = (Long) session.getAttribute("userId");
		
		if(userId == null) {
			return "redirect:/login";
		}
		
		
		Users users = service.findUserById(userId);
		if(users != null) {

			
			transactionsService.saveOrUpdateTransactions(transactionsDTO, users);
			model.addAttribute("success", "Transaction added successfully");
		}
		 if (transactionsDTO.getId() == null) {
		        model.addAttribute("error", "Transaction ID is missing!");
		        return "redirect:/transactions";
		    }
		else {
			model.addAttribute("error", "User not found!");
		}
		return "redirect:/transactions";
		
		
		
	}
	
	@PostMapping("/transactions/delete/{id}")
	public String deleteTransaction(@PathVariable Long id, HttpSession session, Model model) {
	    Long userId = (Long) session.getAttribute("userId");

	    if (userId == null) {
	        return "redirect:/login";
	    }

	    boolean deleted = transactionsService.deleteTransaction(id, userId);
	    if (deleted) {
	        model.addAttribute("success", "Transaction deleted successfully!");
	    } else {
	        model.addAttribute("error", "Transaction not found or you don't have permission to delete it.");
	    }

	    return "redirect:/transactions"; 
	}

	
	
}
