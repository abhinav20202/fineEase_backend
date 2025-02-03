package com.application.repository;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.application.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	Users findByEmail(String email);
	List<Users> findByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);
	
	 @Query("SELECT u FROM Users u WHERE (u.registrationDate BETWEEN :fromDate AND :toDate) AND (LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
	    List<Users> findByRegistrationDateBetweenAndSearch(LocalDate fromDate, LocalDate toDate, String search);
	 
	    List<Users> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);


}
