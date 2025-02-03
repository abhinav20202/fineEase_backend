package com.application.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.dto.ReportDTO;
import com.application.dto.ReportProjection;
import com.application.service.ReportService;
import com.application.service.TransactionsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReportController {
	
	@Autowired ReportService reportService;
    @Autowired TransactionsService transactionsService; 
	
	@GetMapping("/reports")
	public String generateReport(
		@RequestParam(required = false) String startDate,
		@RequestParam(required = false) String endDate,
		@RequestParam(required = false) String Category,
		@RequestParam(required = false) String search,
		Model model, HttpSession session) {
		
		Long userId = (Long) session.getAttribute("userId");
		if(userId == null) {
			model.addAttribute("error", "userId is required");
		}
		
		LocalDate start = (startDate != null) ? LocalDate.parse(startDate) : LocalDate.now().withDayOfMonth(1);
		LocalDate end = (endDate != null) ? LocalDate.parse(endDate) : LocalDate.now();
		
		List<ReportProjection> report = reportService.generateReport(userId, start, end, Category, search);
		List<String> categories = transactionsService.getAllCategories(userId);
        model.addAttribute("report", report);
		model.addAttribute("categories", categories);
		
		return "reports";
		
	}
		
	}

