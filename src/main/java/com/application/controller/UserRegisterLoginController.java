package com.application.controller;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.application.dto.LoginDTO;
import com.application.dto.UserDTO;
import com.application.entity.Users;
import com.application.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserRegisterLoginController {
	
	@Autowired
	private UserService service;
		
	/*
	 * @GetMapping("/overview") public String getOverview() { return "overview"; }
	 */
	
	@GetMapping("/signup")
	public String processSignup(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		return "signup";		
	}
	
	@GetMapping("/login")
	public String processLogin(Model model) {
		model.addAttribute("user", new UserDTO());
		return "login";		
	}
	
	
	@PostMapping("/signup")
	public String processSignUp(@ModelAttribute UserDTO userDTO, Model model, HttpSession session) {
		
		Users existingUser = service.findByEmail(userDTO.getEmail());
		
		if(existingUser != null) {
			model.addAttribute("error", "Email already exists! Try logging in.");
			return "signup";
		}
		
		if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
			model.addAttribute("error", "Passwords do not match!");
			return "signup";
		}
		userDTO.setRegistrationDate(LocalDate.now());
		Users savedUser = service.saveUser(userDTO);
		session.setAttribute("userId", savedUser.getUser_id());
		session.setAttribute("userName", savedUser.getUsername());
		model.addAttribute("success", "You are Registered Successfully. Kindly Login");
		
		return "redirect:/login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("user") LoginDTO loginDTO, HttpSession session, Model model) {
		
		System.out.println("login request recieved");
		
		 Users users =  service.authenticateUser(loginDTO);
		 
//		 if(users != null) {
//			 session.setAttribute("userEmail", users.getEmail());
//			 session.setAttribute("userId", users.getUser_id());
//			 session.setAttribute("userName", users.getUsername());
//			 session.setAttribute("userRole", users.getRole());
//			 return "redirect:/overview";
//		 }
//		 else {
//		 model.addAttribute("error", "Invalid email or password!");
//		 return "login";
//		 }
		 
		 if (users != null) {
	            session.setAttribute("userEmail", users.getEmail());
	            session.setAttribute("userId", users.getUser_id());
	            session.setAttribute("userName", users.getUsername());
	            session.setAttribute("userRole", users.getRole());

	            if ("ADMIN".equalsIgnoreCase(users.getRole()) && "ADMIN".equalsIgnoreCase(loginDTO.getRole())) {
	                return "redirect:/admin";
	            } else if ("USER".equalsIgnoreCase(users.getRole()) && "USER".equalsIgnoreCase(loginDTO.getRole())) {
	                return "redirect:/overview";
	            } else {
	                model.addAttribute("error", "Invalid Role Selection!");
	                return "login";
	            }
	        } else {
	            model.addAttribute("error", "Invalid email or password!");
	            return "login";
	        }
	    }
	
	}
	
	
	


