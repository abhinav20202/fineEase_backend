package com.application.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.dto.AdminDTO;
import com.application.dto.UserDTO;
import com.application.entity.Users;
import com.application.service.AdminService;
import com.application.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
	
	
	@GetMapping("/admin")
    public String adminDashboard(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String search,
            Model model) {

        LocalDate startDate = null;
        LocalDate endDate = null;
        
        if (fromDate != null && !fromDate.isEmpty()) {
            startDate = LocalDate.parse(fromDate);
        }

        if (toDate != null && !toDate.isEmpty()) {
            endDate = LocalDate.parse(toDate);
        }
        
        List<Users> users;
        if (startDate != null || endDate != null || (search != null && !search.isEmpty())) {
            users = adminService.filterUsers(startDate, endDate, search);
        } else {
            
            users = adminService.getAllUsers();
        }

        
        model.addAttribute("users", users);
        return "admin-dashboard";
    }
	
	
	@PostMapping("/admin/update")
	public String updateUser(@ModelAttribute UserDTO userDTO, Model model) {
	    Users existingUser = userService.findUserById(userDTO.getUserId());
	    
	    if (existingUser != null) {
	        adminService.saveUsers(existingUser,userDTO);
	        model.addAttribute("success", "User updated successfully!");
	    } else {
	    	model.addAttribute("error", "User not found!");
	    }
	    
	    return "redirect:/admin";
	}
	
	@GetMapping("/admin/delete/{id}")
	public String deleteUser(@PathVariable Long id, Model model) {
	    boolean isDeleted = adminService.deleteUserById(id);
	    
	    if (isDeleted) {
	    	model.addAttribute("success", "User deleted successfully!");
	    } else {
	    	model.addAttribute("error", "Failed to delete user. User may not exist.");
	    }
	    
	    return "redirect:/admin";
	}



}
