package com.application.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.dto.UserDTO;
import com.application.entity.Users;
import com.application.repository.UserRepository;

@Service
public class AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private UserRepository userRepository;

    public List<Users> filterUsers(LocalDate fromDate, LocalDate toDate, String search) {
        try {
            log.info("Filtering users with fromDate: {}, toDate: {}, search: {}", fromDate, toDate, search);

            if (fromDate != null && toDate != null && search != null && !search.isEmpty()) {
                return userRepository.findByRegistrationDateBetweenAndSearch(fromDate, toDate, search);
            } else if (fromDate != null && toDate != null) {
                return userRepository.findByRegistrationDateBetween(fromDate, toDate);
            } else if (search != null && !search.isEmpty()) {
                return userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search);
            } else {
                return getAllUsers();
            }
        } catch (Exception e) {
            log.error("Error occurred while filtering users", e);
            return List.of(); 
        }
    }

    public List<Users> getAllUsers() {
        try {
            log.info("Fetching all users");
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Error occurred while fetching all users", e);
            return List.of(); 
        }
    }

    public Users saveUsers(Users existingUsers, UserDTO userDTO) {
        try {
            log.info("Updating user with ID: {}", existingUsers.getUserId());

            existingUsers.setUsername(userDTO.getUsername());
            existingUsers.setEmail(userDTO.getEmail());

            Users savedUser = userRepository.save(existingUsers);
            log.info("User successfully updated with ID: {}", savedUser.getUserId());

            return savedUser;
        } catch (Exception e) {
            log.error("Error occurred while updating user with ID: {}", existingUsers.getUserId(), e);
            return null; 
        }
    }

    public boolean deleteUserById(Long id) {
        try {
            log.info("Deleting user with ID: {}", id);

            Optional<Users> user = userRepository.findById(id);
            if (user.isPresent()) {
                userRepository.deleteById(id);
                log.info("User successfully deleted with ID: {}", id);
                return true;
            } else {
                log.warn("User not found with ID: {}", id);
                return false;
            }
        } catch (Exception e) {
            log.error("Error occurred while deleting user with ID: {}", id, e);
            return false; 
        }
    }
}