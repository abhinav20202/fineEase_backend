package com.application.service;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.DailyExpenseDTO;
import com.application.dto.DailyIncomeDTO;
import com.application.dto.ExpenseCategoryChartDTO;
import com.application.dto.TransactionsDTO;
import com.application.entity.Transactions;
import com.application.entity.Users;
import com.application.projections.CatgoriesChartProjection;
import com.application.projections.DailyExpenseProjection;
import com.application.repository.SettingsRepository;
import com.application.repository.TransactionsRepository;

import jakarta.transaction.Transaction;


@Service
public class TransactionsService {
	
	private static final Logger log = LoggerFactory.getLogger(TransactionsService.class);
	
	@Autowired TransactionsRepository transactionsRepository;
	@Autowired SettingsRepository settingsRepository;
	
	public Transactions saveOrUpdateTransactions(TransactionsDTO transactionsDTO, Users users) {
		
		//transactionsRepository.findById(users.getUser_id()).orElse(null);
		Transactions transactions;
        if(transactionsDTO.getId() != null) {
        	transactions = transactionsRepository.findById(transactionsDTO.getId()).orElse(null);
        }
        else {
        	transactions = new Transactions();
        }
		
		
		transactions.setAmount(transactionsDTO.getAmount());	
		transactions.setCategory(transactionsDTO.getCategory());
		transactions.setPayMode(transactionsDTO.getPayMode());	
		transactions.setCreatedDate(transactionsDTO.getCreatedDateTime());
		transactions.setPayee(transactionsDTO.getPayee());
		transactions.setAccount(transactionsDTO.getAccount());
		
		transactions.setUsers(users);
		
	    Transactions transactionx=	transactionsRepository.save(transactions);
		return transactionx;
		
		}
	
	public List<TransactionsDTO> getTransactionsByUserId(Long userId){
		
		List<Transactions> transactions = transactionsRepository.findByUsers_UserId(userId);
		return 	 transactions.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	public TransactionsDTO convertToDTO(Transactions transactions) {
	TransactionsDTO dto = new TransactionsDTO();
		dto.setId(transactions.getId());
		dto.setCategory(transactions.getCategory());
		dto.setCreatedDateTime(transactions.getCreatedDate());
		dto.setPayee(transactions.getPayee());
		dto.setAmount(transactions.getAmount());
		dto.setAccount(transactions.getAccount());
		return dto;
	}
	
	public Transactions getTransactionById(Long id) {
		return transactionsRepository.findById(id).orElse(null);
		
	}
	
	public boolean deleteTransaction(Long transactionId, Long userId) {
        Optional<Transactions> transaction = transactionsRepository.findById(transactionId);

        if (transaction.isPresent() && transaction.get().getUsers().getUser_id().equals(userId)) {
            transactionsRepository.deleteById(transactionId);
            return true; 
        }
        return false; 
    }
	
	public Double getTotalExpenseForCurrentMonth(Long userId) {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate today = LocalDate.now();

        Double totalExpense = transactionsRepository.getTotalExpenseForUser(userId, firstDayOfMonth, today);
        return (totalExpense != null) ? totalExpense : 0.0;
    }
	
	public List<String> getAllCategories(Long userId){
		return transactionsRepository.findDistinctCategoriesByUserId(userId);
	}
	
	public List<ExpenseCategoryChartDTO> getExpenseByCategory(Long userId, LocalDate startDate, LocalDate endDate){
		
		List<CatgoriesChartProjection> Projections = transactionsRepository.findBycategory(userId, startDate, endDate);
	return	Projections.stream()
		    .map(p -> new ExpenseCategoryChartDTO(p.getExpenseCategory(), p.getCategoryExpense()))
		    .collect(Collectors.toList());
		    
		

	}
	
	public List<DailyExpenseDTO> getDailyExpense(Long userId, LocalDate startDate, LocalDate endDate){
	
	  List<DailyExpenseProjection> expenseList 	= transactionsRepository.getDailyExpenses(userId, startDate, endDate);
	  return expenseList.stream()
			  .map(p -> new DailyExpenseDTO(p.getExpenseDates(), p.getExpenseAmount())
					  ).collect(Collectors.toList());
	
	}
	
	
	public List<DailyIncomeDTO> getDailyIncome(Long userId, LocalDate startDate, LocalDate endDate) {
        
		Double monthlyIncome = settingsRepository.monthlyIncome(userId);
		List<DailyIncomeDTO> dailyIncomeList = new ArrayList<>();
        int daysInMonth = startDate.lengthOfMonth(); 	
        int todayDayOfMonth = LocalDate.now().getDayOfMonth();
        Double dailyIncome = monthlyIncome / daysInMonth;

        for (int i = 1; i <= todayDayOfMonth; i++) {
            LocalDate date = startDate.withDayOfMonth(i);
            dailyIncomeList.add(new DailyIncomeDTO(date, dailyIncome));
        }
        return dailyIncomeList;
    }
}

