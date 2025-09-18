package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IEmailService;
import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.dto.ValidateEmailDto;
import com.siesth.mothus.model.User;
import com.siesth.mothus.model.UserLanguage;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * This class is used to manage the login page.
 */
@Controller
public class ManageLogin {
    /**
     * This field is used to get the message from the message source.
     */
    private final MessageSource messageSource;

    /**
     * This field is used to manage the user.
     */
    @Autowired
    private IUserManagement userManagement;

    @Autowired
    private UserRepository userRepository;

    /**
     * This field is used to manage the email service.
     */
    @Autowired
    private IEmailService emailService;

    /**
     * This constructor is used to autowire the message source.
     * @param messageSource the message source
     */
    @Autowired
    public ManageLogin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    private AuthenticationManager authManager;

    /**
     * This method is used to show the login page.
     * It adds the texts to the model from locale.
     * @param authentication the authentication
     * @param model          the model
     * @param locale         the locale
     * @return the login page
     */
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

        // locale BEGIN
        String pageTitle = messageSource.getMessage("Login.PageTitle", null, locale);
        String loginButton = messageSource.getMessage("Login.LoginButton", null, locale);
        String registerButton = messageSource.getMessage("Login.RegisterButton", null, locale);
        String playAsInviteButton = messageSource.getMessage("Login.PlayAsInviteButton", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("loginButton", loginButton);
        model.addAttribute("registerButton", registerButton);
        model.addAttribute("playAsInviteButton", playAsInviteButton);
        // locale END

        return "login";
    }

    /**
     * This method is used to show the login content.
     * It adds the texts to the model from locale.
     * @param model  the model
     * @param locale the locale
     * @return the login content page
     */
    @GetMapping("/loginContent")
    public String loginContent(Model model, Locale locale) {
        // Redirect to /login (which will redirect further if needed) if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/login";
        }

        // To pass data to the template
        Object registrationError = model.asMap().get("registrationError");
        Object loginError = model.asMap().get("loginError");

        if (registrationError != null) {
            model.addAttribute("registrationError", registrationError);
            String registrationErrorMessage = messageSource.getMessage("Login.Messages.RegistrationErrorMessage", null, locale);
            model.addAttribute("registrationErrorMessage", registrationErrorMessage);
        }
        if (loginError != null) {
            model.addAttribute("loginError", loginError);
            String loginErrorMessage = messageSource.getMessage("Login.Messages.LoginErrorMessage", null, locale);
            model.addAttribute("loginErrorMessage", loginErrorMessage);
        }

        // locale BEGIN
        String passwordLabel = messageSource.getMessage("Login.LoginContent.PasswordLabel", null, locale);
        String usernameLabel = messageSource.getMessage("Login.LoginContent.UsernameLabel", null, locale);
        String loginLabel = messageSource.getMessage("Login.LoginContent.LoginLabel", null, locale);

        model.addAttribute("passwordLabel", passwordLabel);
        model.addAttribute("usernameLabel", usernameLabel);
        model.addAttribute("loginLabel", loginLabel);
        // locale END
        model.addAttribute("registrationDto", new RegistrationDto());
        return "Content/loginContent";
    }

    /**
     * This method is used to show the register content.
     * It adds the texts to the model from locale.
     * @param model  the model
     * @param locale the locale
     * @return the register content page
     */
    @GetMapping("/registerContent")
    public String registerContent(Model model, Locale locale) {
        // Redirect to /login (which will redirect further if needed) if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/login";
        }

        // To pass data to the template
        Object registrationSuccess = model.asMap().get("registrationSuccess");

        if (registrationSuccess != null) {
            // Pass the success message to the view
            model.addAttribute("registrationSuccess", registrationSuccess);
            String registrationSuccessMessage = messageSource.getMessage("Login.Messages.RegistrationSuccessMessage", null, locale);
            model.addAttribute("registrationSuccessMessage", registrationSuccessMessage);
        }

        // locale BEGIN
        String passwordLabel = messageSource.getMessage("Login.RegisterContent.PasswordLabel", null, locale);
        String usernameLabel = messageSource.getMessage("Login.RegisterContent.UsernameLabel", null, locale);
        String emailLabel = messageSource.getMessage("Login.RegisterContent.EmailLabel", null, locale);
        String registerLabel = messageSource.getMessage("Login.RegisterContent.RegisterLabel", null, locale);
        String confirmPasswordLabel = messageSource.getMessage("Login.RegisterContent.ConfirmPasswordLabel", null, locale);

        model.addAttribute("passwordLabel", passwordLabel);
        model.addAttribute("usernameLabel", usernameLabel);
        model.addAttribute("emailLabel", emailLabel);
        model.addAttribute("registerLabel", registerLabel);
        model.addAttribute("confirmPasswordLabel", confirmPasswordLabel);
        // locale END
        model.addAttribute("registrationDto", new RegistrationDto());
        return "Content/registerContent";
    }

    /**
     * This method is used to show the email validation content.
     * It adds the texts to the model from locale.
     * @param registrationDto    the registration data from the form
     * @param redirectAttributes to pass data to the template
     * @param locale             the locale
     * @return a redirection to the login page
     */
    @PostMapping("/processRegister")
    public String processRegister(HttpServletRequest request, @ModelAttribute("registrationDto") RegistrationDto registrationDto, RedirectAttributes redirectAttributes, Locale locale) {
        // Redirect to /login (which will redirect further if needed) if the user is already logged in
        if (isAuthenticated()) {
            return "redirect:/login";
        }

        if (!registrationDto.getPassword().equals(registrationDto.getPasswordConfirm())) {
            redirectAttributes.addFlashAttribute("registrationPasswordError", "Registration failed. Passwords don't match.");
            return "redirect:/login";
        }

        boolean isGood = userManagement.createNewUser(registrationDto, UserLanguage.fromLocaleOrEn(locale));
        if (isGood) {
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

            String subject = messageSource.getMessage("Login.Email.Subject", null, locale);
            String body1 = messageSource.getMessage("Login.Email.Body1", null, locale);
            String body2 = messageSource.getMessage("Login.Email.Body2", new Object[]{validationCode, minutesDuration}, locale);
            String body3 = messageSource.getMessage("Login.Email.Body3", null, locale);
            String body = body1 + "\n\n" + body2 + "\n\n" + body3;
            emailService.sendEmail(registrationDto.getEmail(), subject, body);
            String pendingRegistrationMessage = messageSource.getMessage("Login.Messages.PendingRegistrationMessage", null, locale);
            redirectAttributes.addFlashAttribute("pendingRegistration", pendingRegistrationMessage);
            redirectAttributes.addFlashAttribute("pendingRegistrationMessage", pendingRegistrationMessage);
        } else {
            String registrationErrorMessage = messageSource.getMessage("Login.Messages.RegistrationErrorMessage", null, locale);
            redirectAttributes.addFlashAttribute("registrationError", registrationErrorMessage);
            redirectAttributes.addFlashAttribute("registrationErrorMessage", registrationErrorMessage);
        }
        return "redirect:/login";
    }

    /**
     * Validate the email
     * It is used to validate the email of the user after clicking on the validate email address button in the email validation page
     * @param authentication     the authentication
     * @param validateEmailDto   the username and the validation code
     * @param redirectAttributes to pass data to the template
     * @param locale             the locale
     * @return where to redirect
     */
    @PostMapping("/validateMailRegister")
    public String validateMailRegister(Authentication authentication, @ModelAttribute("ValidateEmailDto") ValidateEmailDto validateEmailDto, RedirectAttributes redirectAttributes, Locale locale) {
        // Redirect to playZone if the user has already validated his email
        if (isRoleUser()) {
            return "redirect:/playZone";
        }

        boolean isGood = emailService.checkValidationCode(authentication.getName(), validateEmailDto.getCode());
        if (isGood) {
            // If the validation code is correct, we remove it from the database
            emailService.removeValidationCode(authentication.getName());

            String registrationSuccessMessage = messageSource.getMessage("Login.Messages.RegistrationSuccessMessage", null, locale);
            redirectAttributes.addFlashAttribute("registrationSuccess", registrationSuccessMessage);
            redirectAttributes.addFlashAttribute("registrationSuccessMessage", registrationSuccessMessage);
        } else {
            String wrongCodeRegistrationMessage = messageSource.getMessage("Login.Messages.WrongCodeRegistrationMessage", null, locale);
            redirectAttributes.addFlashAttribute("wrongCodeRegistration", wrongCodeRegistrationMessage);
            redirectAttributes.addFlashAttribute("wrongCodeRegistrationMessage", wrongCodeRegistrationMessage);
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
    @ResponseBody
    public int timeBeforeValidationCode(Authentication authentication) {
        // Redirect to playZone if the user has already validated his email
        if (isRoleUser()) {
            return 0;
        }

        return emailService.getValidationCodeTime(authentication.getName());
    }

    /**
     * Create a new validation code
     * It is used to create a new validation code when the user clicks on the "resend validation code" button in the email validation page
     * @param authentication the authentication of the user
     * @param locale         the locale
     */
    @PostMapping("/createNewValidationCode")
    public void createNewValidationCode(Authentication authentication, Locale locale) {

        if (authentication != null) {
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                locale = userManagement.getLanguageByUsername(currentUserName).toLocale();
            }
        }

        // Exit immediately if the user has already validated his email
        if (isRoleUser() || authentication == null) {
            return;
        }

        // Recreate a validation code
        String username = authentication.getName();
        emailService.createNewValidationCode(username);
        User user = userRepository.findUserByUsername(username);

        // Send a new mail
        String validationCode = emailService.getValidationCode(username);
        int minutesDuration = emailService.getDurationMinutes(username);
        String subject = messageSource.getMessage("Login.Email.Subject", null, locale);
        String body1 = messageSource.getMessage("Login.Email.Body1", null, locale);
        String body2 = messageSource.getMessage("Login.Email.Body2", new Object[]{validationCode, minutesDuration}, locale);
        String body3 = messageSource.getMessage("Login.Email.Body3", null, locale);
        String body = body1 + "\n\n" + body2 + "\n\n" + body3;
        emailService.sendEmail(user.getMail(), subject, body);
    }

    /**
     * This method is used to show the email validation content.
     * It adds the texts to the model from locale.
     * @param model  the model
     * @param locale the locale
     * @return the email validation content page
     */
    @GetMapping("/confirmEmailPopup")
    public String ConfirmEmailPopup(Model model, Locale locale) {
        // TODO : Change email popup to a content instead, and automatically show it on /login if user has role ROLE_PENDING
        // TODO : Add a log out button
        // Redirect to playZone if the user has already validated his email
        if (isRoleUser()) {
            return "redirect:/playZone";
        }

        model.addAttribute("validateEmailDto", new ValidateEmailDto());

        // locale BEGIN
        String confirmEmailLabel = messageSource.getMessage("Login.ConfirmEmailPopup.ConfirmEmailLabel", null, locale);
        String popupLabel;
        if (emailService.isAvailable()) {
            popupLabel = messageSource.getMessage("Login.ConfirmEmailPopup.PopupLabel", null, locale);
        } else {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String validationCode = emailService.getValidationCode(username);
            popupLabel = messageSource.getMessage("Login.ConfirmEmailPopup.PopupLabelNotConfigured", new Object[]{validationCode}, locale);
        }
        String resendCodeLabel = messageSource.getMessage("Login.ConfirmEmailPopup.ResendCodeLabel", null, locale);
        String timeRemainingLabel1 = messageSource.getMessage("Login.ConfirmEmailPopup.TimeRemainingLabel1", null, locale);
        String timeRemainingLabel2 = messageSource.getMessage("Login.ConfirmEmailPopup.TimeRemainingLabel2", null, locale);

        model.addAttribute("confirmEmailLabel", confirmEmailLabel);
        model.addAttribute("popupLabel", popupLabel);
        model.addAttribute("resendCodeLabel", resendCodeLabel);
        model.addAttribute("timeRemainingLabel1", timeRemainingLabel1);
        model.addAttribute("timeRemainingLabel2", timeRemainingLabel2);
        // locale END

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