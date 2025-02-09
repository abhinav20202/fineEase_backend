package com.application.service;


import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.LoginDTO;
import com.application.dto.UserDTO;
import com.application.entity.Settings;
import com.application.entity.Users;
import com.application.repository.SettingsRepository;
import com.application.repository.UserRepository;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired SettingsRepository settingsRepository;
	
	public Users findByEmail(String email){
		logger.info("Fetching user by email: {}", email);
	//	return userRepository.findByEmail(email);
		try {
		 Users user = userRepository.findByEmail(email);
		 if (user == null) {
             logger.warn("User not found with email: {}", email);
         } else {
             logger.debug("User found: {}", user.getUsername());
         }
         return user;
     }
	catch (Exception e) {
        logger.error("Error fetching user by email {}: {}", email, e.getMessage());
        return null;
    }
	}
	
	public Users saveUser(UserDTO userDTO) {
		logger.info("Registering new user with email: {}", userDTO.getEmail());
     	try {
		Users user = new Users();
     	user.setUsername(userDTO.getUsername());
     	user.setEmail(userDTO.getEmail());
     	user.setPassword(userDTO.getPassword());
     	user.setRole("USER");
     	
     	if (userDTO.getRegistrationDate() == null) {
            user.setRegistrationDate(LocalDate.now());
        } else {
            user.setRegistrationDate(userDTO.getRegistrationDate());
        }
     	user = userRepository.save(user);
        logger.info("User registered successfully: {}", user.getEmail());

		 user = userRepository.save(user);
		Settings settings = new Settings();
		settings.setUser(user);
		settingsRepository.save(settings);
		logger.debug("Settings created for user: {}", user.getEmail());

		return user;
     	}
     	catch (Exception e) {
            logger.error("Error registering user {}: {}", userDTO.getEmail(), e.getMessage());
            return null;
        }
	}
	
	public Users authenticateUser(LoginDTO loginDTO) {
		 logger.info("Authenticating user with email: {}", loginDTO.getEmail());
		 
		 try {
		Users userDetail = userRepository.findByEmail(loginDTO.getEmail());
		System.out.println(userDetail+" "+loginDTO.getPassword());
	
		if(userDetail == null) {
			logger.warn("Authentication failed: No user found with email {}", loginDTO.getEmail());
			return null;
		}
		
	if (loginDTO.getPassword().equals(userDetail.getPassword())) {
		logger.info("User authenticated successfully: {}", loginDTO.getEmail());
		System.out.println("Password Matched");
		return userDetail;
	}
	else {
		logger.warn("Authentication failed: Incorrect password for email {}", loginDTO.getEmail());
	}
		return null;
	}
		 catch (Exception e) {
	            logger.error("Error during authentication for {}: {}", loginDTO.getEmail(), e.getMessage());
	            return null;
	        }
	}
	
   public Users findUserById(Long userId) {
	   logger.info("Fetching user by ID: {}", userId);
	   try {
           Optional<Users> user = userRepository.findById(userId);

           if (user.isPresent()) {
               logger.debug("User found: {}", user.get().getUsername());
               return user.get();
           } else {
               logger.warn("User not found with ID: {}", userId);
               return null;
           }
       } catch (Exception e) {
           logger.error("Error fetching user by ID {}: {}", userId, e.getMessage());
           return null;
       }
   }
   
   
   
   
   }
