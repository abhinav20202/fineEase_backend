package com.application.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.TransactionsDTO;
import com.application.entity.Transactions;
import com.application.entity.Users;
import com.application.repository.TransactionsRepository;

import jakarta.transaction.Transaction;


@Service
public class TransactionsService {
	
	@Autowired TransactionsRepository transactionsRepository;
	
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
		return transactionsRepository.findDistinctCategoryByUsersUserId(userId);
	}
	
}
