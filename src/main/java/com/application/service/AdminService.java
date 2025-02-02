package com.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.UserDTO;
import com.application.entity.Users;
import com.application.repository.UserRepository;

@Service
public class AdminService {
	
	 @Autowired
	    private UserRepository userRepository;

	    public List<Users> filterUsers(LocalDate fromDate, LocalDate toDate, String search) {
	    	
	        if (fromDate != null && toDate != null && search != null && !search.isEmpty()) {
	        	
	            return userRepository.findByRegistrationDateBetweenAndSearch(fromDate, toDate, search);
	        }
	        else if (fromDate != null && toDate != null) {
	        	
	            return userRepository.findByRegistrationDateBetween(fromDate, toDate);
	            
	        } else if (search != null && !search.isEmpty()) {
	        	
	            return userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search);
	            
	        } else {
	            return getAllUsers(); 
	        }
	    }
	
	    public List<Users> getAllUsers() {
	        return userRepository.findAll();
	    }
	    
	    public Users saveUsers(Users existingUsers, UserDTO userDTO) {
	    	
	    		existingUsers.setUsername(userDTO.getUsername());
	    		existingUsers.setEmail(userDTO.getEmail());
	    		return userRepository.save(existingUsers);
	    }

	    public boolean deleteUserById(Long id) {
	        Optional<Users> user = userRepository.findById(id);
	        
	        if (user.isPresent()) {
	            userRepository.deleteById(id);
		            return true;
	        }
	        return false;
	    }
	    
}
