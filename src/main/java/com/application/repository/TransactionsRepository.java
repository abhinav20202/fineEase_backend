package com.application.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.entity.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
	
	List<Transactions> findByUsers_UserId(Long userId);
	void deleteById(Long id);
	  
	@Query("SELECT COALESCE(SUM(t.amount), 0.0) FROM Transactions t WHERE t.users.id = :userId AND t.createdDate BETWEEN :startDate AND :endDate")
	Double getTotalExpenseForUser(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}
