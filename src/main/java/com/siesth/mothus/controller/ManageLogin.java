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
    public String loginContent(Model model) {
        // To pass data to the template
        Object registrationError = model.asMap().get("registrationError");
        Object loginError = model.asMap().get("loginError");

        if (registrationError != null) {
            model.addAttribute("registrationError", registrationError);
        }
        if(loginError != null) {
            model.addAttribute("loginError", loginError);
        }
        model.addAttribute("registrationDto", new RegistrationDto());
        return "Content/loginContent";
    }

    @GetMapping("/registerContent")
    public String registerContent(Model model) {
        // To pass data to the template
        Object registrationSuccess = model.asMap().get("registrationSuccess");

        if (registrationSuccess != null) {
            // Pass the success message to the view
            model.addAttribute("registrationSuccess", registrationSuccess);
        }
        model.addAttribute("registrationDto", new RegistrationDto());
        return "Content/registerContent";
    }

    @PostMapping("/processRegister")
    public String processRegister(@ModelAttribute("registrationDto") RegistrationDto registrationDto , RedirectAttributes redirectAttributes) {
        boolean isGood = userManager.createNewUser(registrationDto);
        if(isGood) {
            redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful. You can now log in.");
        }
        else {
            redirectAttributes.addFlashAttribute("registrationError", "Registration failed. Username or email already exists.");
        }
        return "redirect:/login";
    }

    @PostMapping("/processLogin")
    public String processLogin(@ModelAttribute("registrationDto") RegistrationDto registrationDto , RedirectAttributes redirectAttributes) {
        boolean isGood = userManager.checkLogin(registrationDto);
        if(isGood) {
            return "redirect:/playZone";
        }
        else {
            redirectAttributes.addFlashAttribute("loginError", "Login failed. Username or password is incorrect.");
            return "redirect:/login";
        }
    }
}