package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.dto.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;

@Controller
public class ManageLogin {
    private final MessageSource messageSource;
    @Autowired
    private IUserManagement userManager;
    @Autowired
    public ManageLogin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/login")
    public String login(Model model, Locale locale) {
        String pageTitle = messageSource.getMessage("Login.PageTitle", null, locale);
        String loginButton = messageSource.getMessage("Login.LoginButton", null, locale);
        String registerButton = messageSource.getMessage("Login.RegisterButton", null, locale);
        String playAsInviteButton = messageSource.getMessage("Login.PlayAsInviteButton", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("loginButton", loginButton);
        model.addAttribute("registerButton", registerButton);
        model.addAttribute("playAsInviteButton", playAsInviteButton);

        return "login";
    }

    @GetMapping("/loginContent")
    public String loadCoinShopContent(Model model) {
        // To pass data to the template
        model.addAttribute("someData", "Some data for Coin Shop");
        return "Content/loginContent"; // Thymeleaf template name
    }

    @GetMapping("/registerContent")
    public String loadElementCaseContent(Model model) {
        // o pass data to the template
        model.addAttribute("registrationDto", new RegistrationDto());
        return "Content/registerContent"; // Thymeleaf template name
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("registrationDto") RegistrationDto registrationDto) {
        boolean isGood = userManager.createNewUser(registrationDto);
        if(isGood)
            return "redirect:/playZone";
        else
            return "redirect:/registerContent";
    }
}