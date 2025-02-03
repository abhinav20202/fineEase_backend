package com.application.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.dto.ReportProjection;
import com.application.entity.Transactions;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
	
	List<Transactions> findByUsers_UserId(Long userId);
	void deleteById(Long id);
	  
	@Query("SELECT COALESCE(SUM(t.amount), 0.0) FROM Transactions t WHERE t.users.id = :userId AND t.createdDate BETWEEN :startDate AND :endDate")
	Double getTotalExpenseForUser(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query(value = "SELECT t.category, SUM(t.amount), MAX(t.amount), MIN(t.amount), AVG(t.amount) " +
            "FROM transactions t " +
            "WHERE t.user_id = :userId " +
            "AND t.created_date BETWEEN :startDate AND :endDate " +
            "AND (:category IS NULL OR t.category = :category) " +
            "AND (:search IS NULL OR t.payee LIKE %:search% OR t.category LIKE %:search%) " +
            "GROUP BY t.category", nativeQuery = true)
List<ReportProjection> generateReport(@Param("userId") Long userId,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate,
                                   @Param("category") String category,
                                   @Param("search") String search);

//	@Query("SELECT DISTINCT category FROM Transactions  where user_id= :userId")
//	List<String> findCategoriesByUserId(@Param("userId") Long userId);
	
	List<String> findDistinctCategoryByUsersUserId(Long userId);
}
