package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.application.dto.SettingsDTO;
import com.application.service.SettingsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SettingsController {
	
@Autowired SettingsService settingsService;


@GetMapping("/settings")
public String getSettingsPage(Model model, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");

    if (userId == null) {
        return "redirect:/login"; 
    }

    SettingsDTO settingsDTO = settingsService.getSettingsByUserId(userId);
    if (settingsDTO == null) {
        settingsDTO = new SettingsDTO(userId, 0.0, 0.0, 0.0);
    }

    model.addAttribute("settings", settingsDTO);
    return "settings";
}

@PostMapping("/settings/save")
public String saveSettings(@ModelAttribute SettingsDTO settingsDTO, HttpSession session,RedirectAttributes redirectAttributes) {
    Long userId = (Long) session.getAttribute("userId");

    if (userId == null) {
        return "redirect:/login";
    }

    settingsService.saveOrUpdateSettings(userId, settingsDTO);
    redirectAttributes.addFlashAttribute("success", "Settings saved successfully!");
    return "redirect:/settings?success";
}


}
