package com.application.service;


import java.time.LocalDate;
import java.util.Optional;

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
	
	@Autowired
	private UserRepository userRepository;
	@Autowired SettingsRepository settingsRepository;
	
	public Users findByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	public Users saveUser(UserDTO userDTO) {
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
		Settings settings = new Settings();
		settings.setUser(user);
		settingsRepository.save(settings);
		return user;
	}
	
	public Users authenticateUser(LoginDTO loginDTO) {
		Users userDetail = userRepository.findByEmail(loginDTO.getEmail());
		System.out.println(userDetail+" "+loginDTO.getPassword());
	
	if (loginDTO.getPassword().equals(userDetail.getPassword())) {
		System.out.println("Password Matched");
		return userDetail;
	}
	else {
		return null;
	}
	}
	
   public Users findUserById(Long userId) {
	   Optional<Users> user = userRepository.findById(userId);
	   return user.orElse(null);
	   }
   
   
   
   
   }
