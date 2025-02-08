package com.application.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.dto.DailyExpenseDTO;
import com.application.dto.DailyIncomeDTO;
import com.application.dto.ExpenseCategoryChartDTO;
import com.application.dto.SettingsDTO;
import com.application.service.SettingsService;
import com.application.service.TransactionsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpSession;

@Controller
public class OverviewController {
	
	@Autowired SettingsService settingsService;  
	
	@Autowired TransactionsService transactionsService;
	
    @GetMapping("/overview")
    public String getOverviewPage(Model model, HttpSession session) throws JsonProcessingException {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login";
        }
        
        
        
        Double monthlyIncome = settingsService.getMonthlyIncomeByUserId(userId);
        Double totalExpense = transactionsService.getTotalExpenseForCurrentMonth(userId);
        SettingsDTO settingsDTO =settingsService.getSettingsByUserId(userId);
       
       
        
        Double remainingAmount = (monthlyIncome != null ? monthlyIncome : 0.0) - (totalExpense != null ? totalExpense : 0.0);
        
       
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        String formattedFirstDay = firstDayOfMonth.format(formatter);
        String formattedToday = today.format(formatter);
        
        List<ExpenseCategoryChartDTO> expenseData = transactionsService.getExpenseByCategory(userId, firstDayOfMonth, today);
        
        List<DailyExpenseDTO> dailyExpenseDTOs  = transactionsService.getDailyExpense(userId, firstDayOfMonth, today);
        List<DailyIncomeDTO> dailyIncomeDTOs = transactionsService.getDailyIncome(userId, firstDayOfMonth, today);
         
        
        double todayExpense = dailyExpenseDTOs.stream()
                .filter(expense -> expense.getExpenseDate().equals(today))
                .map(DailyExpenseDTO::getDailyExpense)
                .findFirst()
                .orElse(0.0); 

            double totalMonthlyExpense = dailyExpenseDTOs.stream()
                .mapToDouble(DailyExpenseDTO::getDailyExpense)
                .sum();
            
            boolean isDailyLimitExceeded = todayExpense > settingsDTO.getDailySpendLimit();
            boolean isMonthlyLimitExceeded = totalMonthlyExpense > settingsDTO.getMonthlySpendLimit();
            
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.findAndRegisterModules();
        
        String expenseDataJson = "[]";  // Default empty JSON array
        try {
            expenseDataJson = objectMapper.writeValueAsString(expenseData);
            System.out.println(expenseDataJson);

            String dailyIncomeJson = objectMapper.writeValueAsString(dailyIncomeDTOs);
            String dailyExpenseJson = objectMapper.writeValueAsString(dailyExpenseDTOs);
            System.out.println(dailyIncomeJson);
            System.out.println(dailyExpenseJson);
            
            model.addAttribute("monthlyIncome", monthlyIncome);
            model.addAttribute("totalExpense", totalExpense);
            model.addAttribute("remainingAmount", remainingAmount);
            model.addAttribute("firstDayOfMonth", formattedFirstDay);
            model.addAttribute("todayDate", formattedToday);
            model.addAttribute("expenseData", expenseDataJson);         
       //     model.addAttribute("dailyExpenses", dailyExpenseDTOs);
            //      model.addAttribute("dailyIncomes", dailyIncomeDTOs);
            model.addAttribute("dailyExpenses", dailyExpenseJson);
            model.addAttribute("dailyIncomes", dailyIncomeJson);	
            model.addAttribute("dailyLimitExceeded", isDailyLimitExceeded);
            model.addAttribute("monthlyLimitExceeded", isMonthlyLimitExceeded);
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("dailyIncomes", "[]");
            model.addAttribute("dailyExpenses", "[]");
        }

        return "overview";
    }

    
    

    }

 

