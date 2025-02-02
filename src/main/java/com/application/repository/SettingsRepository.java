package com.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.entity.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long>{
	
	Optional<Settings> findByUserUserId(Long userId);

}
