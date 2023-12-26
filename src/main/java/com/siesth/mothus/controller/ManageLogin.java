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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        Object successMessage = model.asMap().get("successMessage");

        if (successMessage != null) {
            // Pass the success message to the view
            model.addAttribute("successMessage", successMessage);
        }
        return "Content/loginContent"; // Thymeleaf template name
    }

    @GetMapping("/registerContent")
    public String loadElementCaseContent(Model model) {
        // To pass data to the template
        Object errorMessage = model.asMap().get("errorMessage");

        if (errorMessage != null) {
            // Pass the success message to the view
            model.addAttribute("errorMessage", errorMessage);
        }
        model.addAttribute("registrationDto", new RegistrationDto());
        return "Content/registerContent"; // Thymeleaf template name
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("registrationDto") RegistrationDto registrationDto , RedirectAttributes redirectAttributes) {
        boolean isGood = userManager.createNewUser(registrationDto);
        if(isGood) {
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Please log in.");
        }
        else {
            redirectAttributes.addFlashAttribute("errorMessage", "Registration failed. Please try again.");
        }
        return "redirect:/login";
    }
}