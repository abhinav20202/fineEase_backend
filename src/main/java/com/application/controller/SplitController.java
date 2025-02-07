package com.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class SplitController {
	
	@GetMapping("/splits")
	public String getMethodName() {
		return "split-dashboard";
	}
	

}
