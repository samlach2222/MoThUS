package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IEmailService;
import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.dto.EmailDto;
import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.dto.ValidateEmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class ManageLogin {
    private final MessageSource messageSource;
    @Autowired
    private IUserManagement userManager;
    @Autowired
    private IEmailService emailService;
    @Autowired
    public ManageLogin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/login")
    public String login(Model model, Locale locale) {
        // Redirect to playZone if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/playZone";
        }

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
        // Redirect to playZone if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/playZone";
        }

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
        // Redirect to playZone if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/playZone";
        }

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
        // Redirect to playZone if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/playZone";
        }

        // TODO : createNewUser is very slow (a few seconds), make it faster
        boolean isGood = userManager.createNewUser(registrationDto); // TODO : Actually the user is created even if he doesn't validate his email
        if(isGood) {
            // TODO : Create service to generate validation code
            int validationCode = 1234;
            // TODO : Create service to generate validation code

            sendEmail(new EmailDto(registrationDto.getEmail(), "MoThUS Registration Validation", "Hello, thank you for register to MoThUS by Siesth. Here is your validation code : " + validationCode + ". Please enter this code in the validation page."));
            redirectAttributes.addFlashAttribute("pendingRegistration", "Please validate email to complete registration.");
        }
        else {
            redirectAttributes.addFlashAttribute("registrationError", "Registration failed. Username or email already exists.");
        }
        return "redirect:/login";
    }

    @PostMapping("/validateMailRegister")
    public String validateMailRegister(@ModelAttribute("ValidateEmailDto") ValidateEmailDto validateEmailDto , RedirectAttributes redirectAttributes) {
        // Redirect to playZone if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/playZone";
        }

        boolean isGood = true; // TODO : Compare here sent code with generated code
        if(isGood) {
            redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful. You can now log in.");
        }
        else {
            redirectAttributes.addFlashAttribute("wrongCodeRegistration", "Please validate email to complete registration.");
        }
        return "redirect:/login";
    }


    @PostMapping("/send-email")
    public String sendEmail(@RequestBody EmailDto emailRequest) {
        // Redirect to playZone if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/playZone";
        }

        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());

        return "Email sent successfully!";
    }

    @GetMapping("/confirmEmailPopup")
    public String ConfirmEmailPopup(Model model) {
        // Redirect to playZone if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/playZone";
        }

        model.addAttribute("validateEmailDto", new ValidateEmailDto());
        return "Popup/confirmEmailPopup";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // True if the user is not anonymous (logged in)
        return !(authentication instanceof AnonymousAuthenticationToken);
    }
}