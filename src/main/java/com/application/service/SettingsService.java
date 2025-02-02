package com.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.SettingsDTO;
import com.application.entity.Settings;
import com.application.entity.Users;
import com.application.repository.SettingsRepository;
import com.application.repository.UserRepository;

@Service
public class SettingsService {
	
	@Autowired UserRepository userRepository;
	@Autowired SettingsRepository settingsRepository;
	
	 public SettingsDTO getSettingsByUserId(Long userId) {
	        Optional<Settings> settingsOptional = settingsRepository.findByUserUserId(userId);
	        
	        if (settingsOptional.isPresent()) {
	            Settings settings = settingsOptional.get();
	            return new SettingsDTO(
	                settings.getUser().getUserId(),
	                settings.getMonthlyIncome(),
	                settings.getDailySpendLimit(),
	                settings.getMonthlySpendLimit()
	            );
	        }
	        
	        return null; 
	    }

	 public SettingsDTO saveOrUpdateSettings(Long userId, SettingsDTO settingsDTO) {
	        Optional<Users> userOptional = userRepository.findById(userId);

	        if (userOptional.isPresent()) {
	            Users user = userOptional.get();

	            Optional<Settings> settingsOptional = settingsRepository.findByUserUserId(userId);
	            Settings settings = settingsOptional.orElse(new Settings());
	            
	            settings.setUser(user);
	            settings.setMonthlyIncome(settingsDTO.getMonthlyIncome());
	            settings.setDailySpendLimit(settingsDTO.getDailySpendLimit());
	            settings.setMonthlySpendLimit(settingsDTO.getMonthlySpendLimit());

	            settingsRepository.save(settings);
	            return settingsDTO;
	        }

	        return null;

}
	 
	    public Double getMonthlyIncomeByUserId(Long userId) {
	        return settingsRepository.findByUserUserId(userId)
	                .map(Settings::getMonthlyIncome)
	                .orElse(0.0);
	    }
}
