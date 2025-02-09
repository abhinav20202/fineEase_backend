package com.application.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.SettingsDTO;
import com.application.entity.Settings;
import com.application.entity.Users;
import com.application.repository.SettingsRepository;
import com.application.repository.UserRepository;

@Service
public class SettingsService {

    private static final Logger log = LoggerFactory.getLogger(SettingsService.class);

    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private SettingsRepository settingsRepository;

    public SettingsDTO getSettingsByUserId(Long userId) {
        log.info("Fetching settings for user ID: {}", userId);
        
        try {
            Optional<Settings> settingsOptional = settingsRepository.findByUserUserId(userId);

            if (settingsOptional.isPresent()) {
                Settings settings = settingsOptional.get();
                log.info("Settings found for user ID: {}", userId);
                
                return new SettingsDTO(
                    settings.getUser().getUserId(),
                    settings.getMonthlyIncome(),
                    settings.getDailySpendLimit(),
                    settings.getMonthlySpendLimit()
                );
            } else {
                log.warn("No settings found for user ID: {}", userId);
            }
        } catch (Exception e) {
            log.error("Error fetching settings for user ID: {}", userId, e);
        }

        return null;
    }

    public SettingsDTO saveOrUpdateSettings(Long userId, SettingsDTO settingsDTO) {
        log.info("Saving or updating settings for user ID: {}", userId);
        
        try {
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
                log.info("Settings saved/updated successfully for user ID: {}", userId);
                return settingsDTO;
            } else {
                log.warn("User not found for ID: {}, settings not saved.", userId);
            }
        } catch (Exception e) {
            log.error("Error saving/updating settings for user ID: {}", userId, e);
        }

        return null;
    }

    public Double getMonthlyIncomeByUserId(Long userId) {
        log.info("Fetching monthly income for user ID: {}", userId);
        
        try {
            return settingsRepository.findByUserUserId(userId)
                    .map(Settings::getMonthlyIncome)
                    .orElseGet(() -> {
                        log.warn("Monthly income not found for user ID: {}, returning default 0.0", userId);
                        return 0.0;
                    });
        } catch (Exception e) {
            log.error("Error fetching monthly income for user ID: {}", userId, e);
            return 0.0;
        }
    }
}