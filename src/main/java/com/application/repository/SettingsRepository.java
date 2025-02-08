package com.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.entity.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long>{
	
	Optional<Settings> findByUserUserId(Long userId);
	
	@Query("SELECT s.monthlyIncome FROM Settings s WHERE s.user.id = :userId")
    Double monthlyIncome(@Param("userId") Long userId);
	
	@Query("SELECT s FROM Settings s WHERE s.user.id = :userId")
    Settings findByUserId(@Param("userId") Long userId);


}
