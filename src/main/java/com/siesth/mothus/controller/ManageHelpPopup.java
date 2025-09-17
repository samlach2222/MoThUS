package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IUserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

/**
 * This class is used to manage the help popup.
 */
@Controller
public class ManageHelpPopup {
    /**
     * This field is used to get the message from the message source.
     */
    private final MessageSource messageSource;

    @Autowired
    private IUserManagement userManagement;

    /**
     * This constructor is used to autowire the message source.
     * @param messageSource the message source
     */
    @Autowired
    public ManageHelpPopup(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * This method is used to show the help popup.
     * It adds the texts to the model from locale.
     * @param model  the model
     * @param locale the locale
     * @return the help popup page
     */
    @GetMapping("/helpPopup")
    public String helpPopup(Model model, Locale locale, Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            locale = userManagement.getLanguageByUsername(currentUserName).toLocale();
        }

        // locale BEGIN
        String pageTitle = messageSource.getMessage("HelpPopup.PageTitle", null, locale);
        String mothusTitle = messageSource.getMessage("HelpPopup.MothusTitle", null, locale);
        String MothusExplanation = messageSource.getMessage("HelpPopup.MothusExplanation", null, locale);
        String mothusAuthors = messageSource.getMessage("HelpPopup.MothusAuthors", null, locale);
        String RulesExplanation = messageSource.getMessage("HelpPopup.RulesExplanation", null, locale);
        String LettersExplanation = messageSource.getMessage("HelpPopup.LettersExplanation", null, locale);
        String rulesTitle = messageSource.getMessage("HelpPopup.RulesTitle", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("mothusTitle", mothusTitle);
        model.addAttribute("MothusExplanation", MothusExplanation);
        model.addAttribute("mothusAuthors", mothusAuthors);
        model.addAttribute("RulesExplanation", RulesExplanation);
        model.addAttribute("LettersExplanation", LettersExplanation);
        model.addAttribute("rulesTitle", rulesTitle);
        // locale END

        return "Popup/helpPopup";
    }
}