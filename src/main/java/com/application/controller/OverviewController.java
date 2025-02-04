package com.application.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.dto.ExpenseCategoryChartDTO;
import com.application.service.SettingsService;
import com.application.service.TransactionsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class OverviewController {
	
	@Autowired SettingsService settingsService;  
	
	@Autowired TransactionsService transactionsService;
	
    @GetMapping("/overview")
    public String getOverviewPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }
        
        
Double monthlyIncome = settingsService.getMonthlyIncomeByUserId(userId);
        
       
        Double totalExpense = transactionsService.getTotalExpenseForCurrentMonth(userId);

       
        Double remainingAmount = (monthlyIncome != null ? monthlyIncome : 0.0) - (totalExpense != null ? totalExpense : 0.0);
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String formattedFirstDay = firstDayOfMonth.format(formatter);
        String formattedToday = today.format(formatter);
        
        List<ExpenseCategoryChartDTO> expenseData = transactionsService.getExpenseByCategory(userId, firstDayOfMonth, today);
        
//        ObjectMapper objectMapper = new ObjectMapper();
//        String expenseDataJson = "";
//        try {
//            expenseDataJson = objectMapper.writeValueAsString(expenseData);
//        } catch (Exception e) {
//            e.printStackTrace();
//    
//        }
        ObjectMapper objectMapper = new ObjectMapper();
        String expenseDataJson = "[]";  // Default empty JSON array
        try {
            expenseDataJson = objectMapper.writeValueAsString(expenseData);
            System.out.println(expenseDataJson);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
            model.addAttribute("monthlyIncome", monthlyIncome);
            model.addAttribute("totalExpense", totalExpense);
            model.addAttribute("remainingAmount", remainingAmount);
            model.addAttribute("firstDayOfMonth", formattedFirstDay);
            model.addAttribute("todayDate", formattedToday);
            model.addAttribute("expenseData", expenseDataJson);

        return "overview";
    }

    
    

    }

