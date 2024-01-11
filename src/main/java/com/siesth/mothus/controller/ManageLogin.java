package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IEmailService;
import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.dto.ValidateEmailDto;
import com.siesth.mothus.model.User;
import com.siesth.mothus.repository.UserRepository;
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
    // TODO : When registering connect to spring with the role ROLE_PENDING
    private final MessageSource messageSource;
    @Autowired
    private IUserManagement userManagement;
    @Autowired
    private UserRepository userRepository;
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
        boolean isGood = userManagement.createNewUser(registrationDto); // TODO : Actually the user is created even if he doesn't validate his email (maybe add a pending column in the user table)
        if(isGood) {
            String validationCode = emailService.getValidationCode(registrationDto.getUsername());
            int a = emailService.getValidationCodeTime(registrationDto.getUsername());
            int minutesDuration = emailService.getDurationMinutes(registrationDto.getUsername());

            emailService.sendEmail(registrationDto.getEmail(), "MoThUS Registration Validation", "Hello, thank you for registering to MoThUS by Siesth. Here is your validation code : " + validationCode + ", it will be valid for " + minutesDuration + " minutes. Please enter this code in the validation page.");
            redirectAttributes.addFlashAttribute("pendingRegistration", "Please validate email to complete registration.");
        }
        else {
            redirectAttributes.addFlashAttribute("registrationError", "Registration failed. Username or email already exists.");
        }
        return "redirect:/login";
    }

    /**
     * Validate the email
     * It is used to validate the email of the user after clicking on the validate email address button in the email validation page
     * @param validateEmailDto the username and the validation code
     * @param redirectAttributes to pass data to the template
     * @return "redirect:/login"
     */
    @PostMapping("/validateMailRegister")
    public String validateMailRegister(@ModelAttribute("ValidateEmailDto") ValidateEmailDto validateEmailDto , RedirectAttributes redirectAttributes) {
        // Redirect to playZone if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/playZone";
        }

        boolean isGood = emailService.checkValidationCode(validateEmailDto.getUsername(), validateEmailDto.getCode());
        if(isGood) {
            // If the validation code is correct, we remove it from the database
            // Therefore a user that has validated his email will have a null validation code
            emailService.removeValidationCode(validateEmailDto.getUsername());
            redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful. You can now log in.");
        }
        else {
            redirectAttributes.addFlashAttribute("wrongCodeRegistration", "Please validate email to complete registration.");
        }
        return "redirect:/login";
    }

    /**
     * Returns the time before the validation code expires
     * It is used to display the time before the validation code expires in the email validation page
     * @param username the username of the user
     * @return the time before the validation code expires
     */
    @PostMapping("/timeBeforeValidationCode")
    public int timeBeforeValidationCode(@RequestBody String username) {
        // Return -1 if the user is already logged in
        if (isAuthenticated()) {
            return -1;
        }

        return emailService.getValidationCodeTime(username);
    }

    /**
     * Create a new validation code
     * It is used to create a new validation code when the user clicks on the "resend validation code" button in the email validation page
     * @param username the username of the user
     */
    @PostMapping("/createNewValidationCode")
    public void createNewValidationCode(@RequestBody String username) {
        // Return nothing if the user is already logged in
        if (isAuthenticated()) {
            return;
        }

        emailService.createNewValidationCode(username);
        User user = userRepository.findUserByUsername(username);
        emailService.sendEmail(user.getMail(), "MoThUS Registration Validation", "Hello, thank you for register to MoThUS by Siesth. Here is your new validation code : " + emailService.getValidationCode(username) + ". Please enter this code in the validation page.");
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