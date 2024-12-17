package com.ECPI.pontaj_application.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public class BasicController {

    protected void addUserToModel(Model model, Authentication authentication) {
        var roles = authentication.getAuthorities().stream()
                .map(String::valueOf)
                .toList();

        model.addAttribute("userName", authentication.getName());
        model.addAttribute("isAdmin", roles.contains("ADMIN"));
        model.addAttribute("isGeneral",roles.contains("GENERAL"));
    }

}
