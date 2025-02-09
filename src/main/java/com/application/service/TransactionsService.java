/*
 * package com.application.service;
 * 
 * 
 * 
 * import java.time.LocalDate; import java.util.ArrayList; import
 * java.util.List; import java.util.Optional; import
 * java.util.stream.Collectors;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service;
 * 
 * import com.application.dto.DailyExpenseDTO; import
 * com.application.dto.DailyIncomeDTO; import
 * com.application.dto.ExpenseCategoryChartDTO; import
 * com.application.dto.TransactionsDTO; import
 * com.application.entity.Transactions; import com.application.entity.Users;
 * import com.application.projections.CatgoriesChartProjection; import
 * com.application.projections.DailyExpenseProjection; import
 * com.application.repository.SettingsRepository; import
 * com.application.repository.TransactionsRepository;
 * 
 * import jakarta.transaction.Transaction;
 * 
 * 
 * @Service public class TransactionsService {
 * 
 * private static final Logger log =
 * LoggerFactory.getLogger(TransactionsService.class);
 * 
 * @Autowired TransactionsRepository transactionsRepository;
 * 
 * @Autowired SettingsRepository settingsRepository;
 * 
 * public Transactions saveOrUpdateTransactions(TransactionsDTO transactionsDTO,
 * Users users) {
 * 
 * //transactionsRepository.findById(users.getUser_id()).orElse(null);
 * Transactions transactions; if(transactionsDTO.getId() != null) { transactions
 * = transactionsRepository.findById(transactionsDTO.getId()).orElse(null); }
 * else { transactions = new Transactions(); }
 * 
 * 
 * transactions.setAmount(transactionsDTO.getAmount());
 * transactions.setCategory(transactionsDTO.getCategory());
 * transactions.setPayMode(transactionsDTO.getPayMode());
 * transactions.setCreatedDate(transactionsDTO.getCreatedDateTime());
 * transactions.setPayee(transactionsDTO.getPayee());
 * transactions.setAccount(transactionsDTO.getAccount());
 * 
 * transactions.setUsers(users);
 * 
 * Transactions transactionx= transactionsRepository.save(transactions); return
 * transactionx;
 * 
 * }
 * 
 * public List<TransactionsDTO> getTransactionsByUserId(Long userId){
 * 
 * List<Transactions> transactions =
 * transactionsRepository.findByUsers_UserId(userId); return
 * transactions.stream().map(this::convertToDTO).collect(Collectors.toList()); }
 * 
 * public TransactionsDTO convertToDTO(Transactions transactions) {
 * TransactionsDTO dto = new TransactionsDTO(); dto.setId(transactions.getId());
 * dto.setCategory(transactions.getCategory());
 * dto.setCreatedDateTime(transactions.getCreatedDate());
 * dto.setPayee(transactions.getPayee());
 * dto.setAmount(transactions.getAmount());
 * dto.setAccount(transactions.getAccount()); return dto; }
 * 
 * public Transactions getTransactionById(Long id) { return
 * transactionsRepository.findById(id).orElse(null);
 * 
 * }
 * 
 * public boolean deleteTransaction(Long transactionId, Long userId) {
 * Optional<Transactions> transaction =
 * transactionsRepository.findById(transactionId);
 * 
 * if (transaction.isPresent() &&
 * transaction.get().getUsers().getUser_id().equals(userId)) {
 * transactionsRepository.deleteById(transactionId); return true; } return
 * false; }
 * 
 * public Double getTotalExpenseForCurrentMonth(Long userId) { LocalDate
 * firstDayOfMonth = LocalDate.now().withDayOfMonth(1); LocalDate today =
 * LocalDate.now();
 * 
 * Double totalExpense = transactionsRepository.getTotalExpenseForUser(userId,
 * firstDayOfMonth, today); return (totalExpense != null) ? totalExpense : 0.0;
 * }
 * 
 * public List<String> getAllCategories(Long userId){ return
 * transactionsRepository.findDistinctCategoriesByUserId(userId); }
 * 
 * public List<ExpenseCategoryChartDTO> getExpenseByCategory(Long userId,
 * LocalDate startDate, LocalDate endDate){
 * 
 * List<CatgoriesChartProjection> Projections =
 * transactionsRepository.findBycategory(userId, startDate, endDate); return
 * Projections.stream() .map(p -> new
 * ExpenseCategoryChartDTO(p.getExpenseCategory(), p.getCategoryExpense()))
 * .collect(Collectors.toList());
 * 
 * 
 * 
 * }
 * 
 * public List<DailyExpenseDTO> getDailyExpense(Long userId, LocalDate
 * startDate, LocalDate endDate){
 * 
 * List<DailyExpenseProjection> expenseList =
 * transactionsRepository.getDailyExpenses(userId, startDate, endDate); return
 * expenseList.stream() .map(p -> new DailyExpenseDTO(p.getExpenseDates(),
 * p.getExpenseAmount()) ).collect(Collectors.toList());
 * 
 * }
 * 
 * 
 * public List<DailyIncomeDTO> getDailyIncome(Long userId, LocalDate startDate,
 * LocalDate endDate) {
 * 
 * Double monthlyIncome = settingsRepository.monthlyIncome(userId);
 * List<DailyIncomeDTO> dailyIncomeList = new ArrayList<>(); int daysInMonth =
 * startDate.lengthOfMonth(); int todayDayOfMonth =
 * LocalDate.now().getDayOfMonth(); Double dailyIncome = monthlyIncome /
 * daysInMonth;
 * 
 * for (int i = 1; i <= todayDayOfMonth; i++) { LocalDate date =
 * startDate.withDayOfMonth(i); dailyIncomeList.add(new DailyIncomeDTO(date,
 * dailyIncome)); } return dailyIncomeList; } }
 * 
 */

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

@Service
public class TransactionsService {
	
    private static final Logger log = LoggerFactory.getLogger(TransactionsService.class);

    @Autowired 
    private TransactionsRepository transactionsRepository;

    @Autowired 
    private SettingsRepository settingsRepository;

    public Transactions saveOrUpdateTransactions(TransactionsDTO transactionsDTO, Users users) {
        log.info("Saving or updating transaction for user: {}", users.getUser_id());

        try {
            Transactions transactions;
            if (transactionsDTO.getId() != null) {
                transactions = transactionsRepository.findById(transactionsDTO.getId()).orElse(null);
            } else {
                transactions = new Transactions();
            }

            if (transactions == null) {
                log.warn("Transaction not found with ID: {}", transactionsDTO.getId());
                return null;
            }

            transactions.setAmount(transactionsDTO.getAmount());	
            transactions.setCategory(transactionsDTO.getCategory());
            transactions.setPayMode(transactionsDTO.getPayMode());	
            transactions.setCreatedDate(transactionsDTO.getCreatedDateTime());
            transactions.setPayee(transactionsDTO.getPayee());
            transactions.setAccount(transactionsDTO.getAccount());
            transactions.setUsers(users);

            Transactions savedTransaction = transactionsRepository.save(transactions);
            log.info("Transaction saved successfully with ID: {}", savedTransaction.getId());

            return savedTransaction;
        } catch (Exception e) {
            log.error("Error saving transaction: {}", e.getMessage(), e);
            return null;
        }
    }

    public List<TransactionsDTO> getTransactionsByUserId(Long userId) {
        log.info("Fetching transactions for user ID: {}", userId);
        
        try {
            List<Transactions> transactions = transactionsRepository.findByUsers_UserId(userId);
            return transactions.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching transactions for user ID {}: {}", userId, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public TransactionsDTO convertToDTO(Transactions transactions) {
        log.debug("Converting transaction entity to DTO for transaction ID: {}", transactions.getId());
        
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
        log.info("Fetching transaction by ID: {}", id);
        
        try {
            return transactionsRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Error fetching transaction by ID {}: {}", id, e.getMessage(), e);
            return null;
        }
    }

    public boolean deleteTransaction(Long transactionId, Long userId) {
        log.info("Attempting to delete transaction ID: {} for user ID: {}", transactionId, userId);
        
        try {
            Optional<Transactions> transaction = transactionsRepository.findById(transactionId);

            if (transaction.isPresent() && transaction.get().getUsers().getUser_id().equals(userId)) {
                transactionsRepository.deleteById(transactionId);
                log.info("Transaction ID {} deleted successfully", transactionId);
                return true;
            } else {
                log.warn("Transaction ID {} not found or does not belong to user ID {}", transactionId, userId);
                return false;
            }
        } catch (Exception e) {
            log.error("Error deleting transaction ID {}: {}", transactionId, e.getMessage(), e);
            return false;
        }
    }

    public Double getTotalExpenseForCurrentMonth(Long userId) {
        log.info("Calculating total expense for the current month for user ID: {}", userId);
        
        try {
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            LocalDate today = LocalDate.now();

            Double totalExpense = transactionsRepository.getTotalExpenseForUser(userId, firstDayOfMonth, today);
            log.debug("Total monthly expense calculated: {}", totalExpense);

            return (totalExpense != null) ? totalExpense : 0.0;
        } catch (Exception e) {
            log.error("Error calculating total expense for user ID {}: {}", userId, e.getMessage(), e);
            return 0.0;
        }
    }

    public List<String> getAllCategories(Long userId) {
        log.info("Fetching distinct categories for user ID: {}", userId);
        
        try {
            return transactionsRepository.findDistinctCategoriesByUserId(userId);
        } catch (Exception e) {
            log.error("Error fetching categories for user ID {}: {}", userId, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<ExpenseCategoryChartDTO> getExpenseByCategory(Long userId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching expense data by category for user ID: {} from {} to {}", userId, startDate, endDate);
        
        try {
            List<CatgoriesChartProjection> projections = transactionsRepository.findBycategory(userId, startDate, endDate);
            return projections.stream()
                .map(p -> new ExpenseCategoryChartDTO(p.getExpenseCategory(), p.getCategoryExpense()))
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching expense by category for user ID {}: {}", userId, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<DailyExpenseDTO> getDailyExpense(Long userId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching daily expenses for user ID: {} from {} to {}", userId, startDate, endDate);
        
        try {
            List<DailyExpenseProjection> expenseList = transactionsRepository.getDailyExpenses(userId, startDate, endDate);
            return expenseList.stream()
                .map(p -> new DailyExpenseDTO(p.getExpenseDates(), p.getExpenseAmount()))
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching daily expenses for user ID {}: {}", userId, e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<DailyIncomeDTO> getDailyIncome(Long userId, LocalDate startDate, LocalDate endDate) {
        log.info("Calculating daily income for user ID: {} from {} to {}", userId, startDate, endDate);
        
        try {
            Double monthlyIncome = settingsRepository.monthlyIncome(userId);
            List<DailyIncomeDTO> dailyIncomeList = new ArrayList<>();
            int daysInMonth = startDate.lengthOfMonth();
            int todayDayOfMonth = LocalDate.now().getDayOfMonth();
            Double dailyIncome = (monthlyIncome != null) ? (monthlyIncome / daysInMonth) : 0.0;

            for (int i = 1; i <= todayDayOfMonth; i++) {
                LocalDate date = startDate.withDayOfMonth(i);
                dailyIncomeList.add(new DailyIncomeDTO(date, dailyIncome));
            }

            return dailyIncomeList;
        } catch (Exception e) {
            log.error("Error calculating daily income for user ID {}: {}", userId, e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}