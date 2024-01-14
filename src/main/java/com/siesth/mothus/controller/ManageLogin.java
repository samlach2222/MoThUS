package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IEmailService;
import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.dto.ValidateEmailDto;
import com.siesth.mothus.model.User;
import com.siesth.mothus.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

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

    @Autowired
    private AuthenticationManager authManager;

    @GetMapping("/login")
    public String login(Authentication authentication, Model model, Locale locale) {
        // Check whether we can change the user role from ROLE_PENDING to ROLE_USER,
        // and redirect to /playZone if user has ROLE_USER
        if (isAuthenticated()) {
            User user = userRepository.findUserByUsername(authentication.getName());
            if (!isRoleUser() && user.getValidationCode() == null) {
                Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

                Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);

                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }

            // Redirect to /playZone whether user already had the role ROLE_USER or got it just now
            if (isRoleUser()) {
                return "redirect:/playZone";
            }
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
        // Redirect to /login (which will redirect further if needed) if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/login";
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
        // Redirect to /login (which will redirect further if needed) if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/login";
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
    public String processRegister(HttpServletRequest request, @ModelAttribute("registrationDto") RegistrationDto registrationDto, RedirectAttributes redirectAttributes) {
        // Redirect to /login (which will redirect further if needed) if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/login";
        }

        if (!registrationDto.getPassword().equals(registrationDto.getPasswordConfirm())) {
            redirectAttributes.addFlashAttribute("registrationPasswordError", "Registration failed. Passwords don't match.");
            return "redirect:/login";
        }

        // TODO : createNewUser is very slow (a few seconds), make it faster
        boolean isGood = userManagement.createNewUser(registrationDto);
        if(isGood) {
            String validationCode = emailService.getValidationCode(registrationDto.getUsername());
            int minutesDuration = emailService.getDurationMinutes(registrationDto.getUsername());

            // Authenticate the user in spring
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(registrationDto.getUsername(), registrationDto.getPassword());
            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

            emailService.sendEmail(registrationDto.getEmail(), "MoThUS Registration Validation", "Hello, thank you for registering to MoThUS by Siesth!\n\nHere is your validation code : " + validationCode + ", it will be valid for " + minutesDuration + " minutes.\n\nPlease enter this code in the validation page.");
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
    public String validateMailRegister(Authentication authentication, @ModelAttribute("ValidateEmailDto") ValidateEmailDto validateEmailDto, RedirectAttributes redirectAttributes) {
        // Redirect to playZone if the user has already validated his email
        if (isRoleUser()) {
            return "redirect:/playZone";
        }

        boolean isGood = emailService.checkValidationCode(authentication.getName(), validateEmailDto.getCode());
        if(isGood) {
            // If the validation code is correct, we remove it from the database
            emailService.removeValidationCode(authentication.getName());
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
     * @param authentication the authentication of the user (automatically passed by Spring)
     * @return the time before the validation code expires
     */
    @PostMapping("/timeBeforeValidationCode")
    public int timeBeforeValidationCode(Authentication authentication) {
        // Redirect to playZone if the user has already validated his email
        if (isRoleUser()) {
            return -1;
        }

        return emailService.getValidationCodeTime(authentication.getName());
    }

    /**
     * Create a new validation code
     * It is used to create a new validation code when the user clicks on the "resend validation code" button in the email validation page
     * @param authentication the authentication of the user (automatically passed by Spring)
     */
    @PostMapping("/createNewValidationCode")
    public void createNewValidationCode(Authentication authentication) {
        // Exit immediately if the user has already validated his email
        if (isRoleUser()) {
            return;
        }

        // Recreate a validation code
        String username = authentication.getName();
        emailService.createNewValidationCode(username);
        User user = userRepository.findUserByUsername(username);

        // Send a new mail
        String validationCode = emailService.getValidationCode(username);
        int minutesDuration = emailService.getDurationMinutes(username);
        emailService.sendEmail(user.getMail(), "MoThUS Registration Validation New Code", "Hello, thank you for registering to MoThUS by Siesth!\n\nHere is your new validation code : " + validationCode + ", it will be valid for " + minutesDuration + " minutes.\n\nPlease enter this code in the validation page.");
    }

    @GetMapping("/confirmEmailPopup")
    public String ConfirmEmailPopup(Model model) {
        // TODO : Change email popup to a content instead, and automatically show it on /login if user has role ROLE_PENDING
        // TODO : Add a log out button
        // Redirect to playZone if the user has already validated his email
        if (isRoleUser()) {
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

    private boolean isRoleUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // True if the user has the role ROLE_PENDING
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // TODO : Add backend and frontend for a "forgot password" feature
}