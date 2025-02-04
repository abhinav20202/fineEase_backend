package com.application.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.dto.ExpenseCategoryChartDTO;
import com.application.dto.ReportProjection;
import com.application.entity.Transactions;
import com.application.projections.CatgoriesChartProjection;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
	
	List<Transactions> findByUsers_UserId(Long userId);
	void deleteById(Long id);
	  
	@Query("SELECT COALESCE(SUM(t.amount), 0.0) FROM Transactions t WHERE t.users.id = :userId AND t.createdDate BETWEEN :startDate AND :endDate")
	Double getTotalExpenseForUser(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
//	@Query(value = "SELECT t.category, SUM(t.amount), MAX(t.amount), MIN(t.amount), AVG(t.amount) " +
//            "FROM transactions t " +
//            "WHERE t.user_id = :userId " +
//            "AND t.created_date BETWEEN :startDate AND :endDate " +
//            "AND (:category IS NULL OR t.category = :category) " +
//            "AND (:search IS NULL OR t.payee LIKE %:search% OR t.category LIKE %:search%) " +
//            "GROUP BY t.category", nativeQuery = true)
//List<ReportProjection> generateReport(@Param("userId") Long userId,
//                                   @Param("startDate") LocalDate startDate,
//                                   @Param("endDate") LocalDate endDate,
//                                   @Param("category") String category,
//                                   @Param("search") String search);
	
//	 @Query("SELECT t.category, SUM(t.amount), MAX(t.amount), MIN(t.amount), AVG(t.amount) " +
//	           "FROM Transactions t " +
//	           "WHERE t.users.id = :userId " +
//	           "AND t.createdDate BETWEEN :startDate AND :endDate " +
//	           "AND (:search IS NULL OR LOWER(t.payee) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(t.category) LIKE LOWER(CONCAT('%', :search, '%'))) " +
//	           "GROUP BY t.category")
//	    List<ReportProjection> generateReports(
//	            @Param("userId") Long userId,
//	            @Param("startDate") LocalDate startDate,
//	            @Param("endDate") LocalDate endDate,
//	            @Param("search") String search
//	    );
	 

	    @Query("SELECT t.category AS category, " +
	           "SUM(t.amount) AS totalSpent, " +
	           "MAX(t.amount) AS highestSpent, " +
	           "MIN(t.amount) AS lowestSpent, " +
	           "AVG(t.amount) AS averageSpent " +
	           "FROM Transactions t " +
	           "WHERE t.users.id = :userId " +
	           "AND t.createdDate BETWEEN :startDate AND :endDate " +
	           "AND (:search IS NULL OR LOWER(t.category) LIKE LOWER(CONCAT('%', :search, '%'))) " +
	           "GROUP BY t.category")
	    List<ReportProjection> generateReport(
	            @Param("userId") Long userId,
	            @Param("startDate") LocalDate startDate,
	            @Param("endDate") LocalDate endDate,
	            @Param("search") String search
	    );



//	@Query("SELECT DISTINCT category FROM Transactions  where user_id= :userId")
//	List<String> findCategoriesByUserId(@Param("userId") Long userId);
	
	    
	        @Query("SELECT DISTINCT t.category FROM Transactions t WHERE t.users.id = :userId")
	        List<String> findDistinctCategoriesByUserId(@Param("userId") Long userId);
	    
	        @Query("SELECT t.category As expenseCategory, SUM(t.amount) AS categoryExpense FROM Transactions t "+
	        "WHERE t.users.id = :userId AND t.createdDate BETWEEN :startDate AND :endDate "+
	        " GROUP BY t.category")		
	        List<CatgoriesChartProjection> findBycategory(@Param("userId") Long userId,
		            @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	        
//	        @Query("SELECT t.category AS expenseCategory, SUM(t.amount) AS categoryExpense " +
//	        	       "FROM Transactions t " +
//	        	       "WHERE t.users.id = :userId AND t.createdDate BETWEEN :startDate AND :endDate " +
//	        	       "GROUP BY t.category")
//	        	List<CatgoriesChartProjection> findByCategory(
//	        	    @Param("userId") Long userId,
//	        	    @Param("startDate") LocalDate startDate,
//	        	    @Param("endDate") LocalDate endDate);
//	        
//	              

	    
}	

