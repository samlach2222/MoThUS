package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IEmailService;
import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.dto.ValidateEmailDto;
import com.siesth.mothus.model.User;
import com.siesth.mothus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    public String loginContent(Model model, Locale locale) {
        // To pass data to the template
        Object registrationError = model.asMap().get("registrationError");
        Object loginError = model.asMap().get("loginError");

        if (registrationError != null) {
            model.addAttribute("registrationError", registrationError);
            String registrationErrorMessage = messageSource.getMessage("Login.Messages.RegistrationErrorMessage", null, locale);
            model.addAttribute("registrationErrorMessage", registrationErrorMessage);
        }
        if(loginError != null) {
            model.addAttribute("loginError", loginError);
            String loginErrorMessage = messageSource.getMessage("Login.Messages.LoginErrorMessage", null, locale);
            model.addAttribute("loginErrorMessage", loginErrorMessage);
        }
        model.addAttribute("registrationDto", new RegistrationDto());
        return "Content/loginContent";
    }

    @GetMapping("/registerContent")
    public String registerContent(Model model, Locale locale) {
        // To pass data to the template
        Object registrationSuccess = model.asMap().get("registrationSuccess");

        if (registrationSuccess != null) {
            // Pass the success message to the view
            model.addAttribute("registrationSuccess", registrationSuccess);
            String registrationSuccessMessage = messageSource.getMessage("Login.Messages.RegistrationSuccessMessage", null, locale);
            model.addAttribute("registrationSuccessMessage", registrationSuccessMessage);
        }
        model.addAttribute("registrationDto", new RegistrationDto());
        return "Content/registerContent";
    }

    @PostMapping("/processRegister")
    public String processRegister(@ModelAttribute("registrationDto") RegistrationDto registrationDto , RedirectAttributes redirectAttributes, Locale locale) {
        // TODO : createNewUser is very slow (a few seconds), make it faster
        boolean isGood = userManagement.createNewUser(registrationDto); // TODO : Actually the user is created even if he doesn't validate his email (maybe add a pending column in the user table)
        if(isGood) {
            int validationCode = emailService.getValidationCode(registrationDto.getUsername());

            emailService.sendEmail(registrationDto.getEmail(), "MoThUS Registration Validation", "Hello, thank you for register to MoThUS by Siesth. Here is your validation code : " + validationCode + ". Please enter this code in the validation page.");
            String pendingRegistrationMessage = messageSource.getMessage("Login.Messages.PendingRegistrationMessage", null, locale);
            redirectAttributes.addFlashAttribute("pendingRegistration", pendingRegistrationMessage);
            redirectAttributes.addFlashAttribute("pendingRegistrationMessage", pendingRegistrationMessage);
        }
        else {
            String registrationErrorMessage = messageSource.getMessage("Login.Messages.RegistrationErrorMessage", null, locale);
            redirectAttributes.addFlashAttribute("registrationError", registrationErrorMessage);
            redirectAttributes.addFlashAttribute("registrationErrorMessage", registrationErrorMessage);
        }
        return "redirect:/login";
    }

    @PostMapping("/processLogin")
    public String processLogin(@ModelAttribute("registrationDto") RegistrationDto registrationDto , RedirectAttributes redirectAttributes, Locale locale) {
        boolean isGood = userManagement.checkLogin(registrationDto);
        if(isGood) {
            return "redirect:/playZone";
        }
        else {
            String loginErrorMessage = messageSource.getMessage("Login.Messages.LoginErrorMessage", null, locale);
            redirectAttributes.addFlashAttribute("loginError", loginErrorMessage);
            redirectAttributes.addFlashAttribute("loginErrorMessage", loginErrorMessage);
            return "redirect:/login";
        }
    }

    /**
     * Validate the email
     * It is used to validate the email of the user after clicking on the validate email address button in the email validation page
     * @param validateEmailDto the username and the validation code
     * @param redirectAttributes to pass data to the template
     * @return "redirect:/login"
     */
    @PostMapping("/validateMailRegister")
    public String validateMailRegister(@ModelAttribute("ValidateEmailDto") ValidateEmailDto validateEmailDto , RedirectAttributes redirectAttributes, Locale locale) {
        boolean isGood = emailService.checkValidationCode(validateEmailDto.getUsername(), validateEmailDto.getCode());
        if(isGood) {
            String registrationSuccessMessage = messageSource.getMessage("Login.Messages.RegistrationSuccessMessage", null, locale);
            redirectAttributes.addFlashAttribute("registrationSuccess", registrationSuccessMessage);
            redirectAttributes.addFlashAttribute("registrationSuccessMessage", registrationSuccessMessage);
        }
        else {
            String wrongCodeRegistrationMessage = messageSource.getMessage("Login.Messages.WrongCodeRegistrationMessage", null, locale);
            redirectAttributes.addFlashAttribute("wrongCodeRegistration", wrongCodeRegistrationMessage);
            redirectAttributes.addFlashAttribute("wrongCodeRegistrationMessage", wrongCodeRegistrationMessage);
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
        return emailService.getValidationCodeTime(username);
    }

    /**
     * Create a new validation code
     * It is used to create a new validation code when the user clicks on the "resend validation code" button in the email validation page
     * @param username the username of the user
     */
    @PostMapping("/createNewValidationCode")
    public void createNewValidationCode(@RequestBody String username, Locale locale) {
        emailService.createNewValidationCode(username);
        User user = userRepository.findUserByUsername(username);

        String subject = messageSource.getMessage("Login.Email.Subject", null, locale);
        String body1 = messageSource.getMessage("Login.Email.Body1", null, locale);
        String body2 = messageSource.getMessage("Login.Email.Body2", null, locale);
        emailService.sendEmail(user.getMail(), subject, body1 + emailService.getValidationCode(username) + body2);
    }


    @GetMapping("/confirmEmailPopup")
    public String ConfirmEmailPopup(Model model) {
        model.addAttribute("validateEmailDto", new ValidateEmailDto());
        return "Popup/confirmEmailPopup";
    }
}