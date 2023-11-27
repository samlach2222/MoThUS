package com.siesth.mothus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class ManageHelpPopup {
    private final MessageSource messageSource;

    @Autowired
    public ManageHelpPopup(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/helpPopup")
    public String helpPopup(Model model, Locale locale) {
        String pageTitle = messageSource.getMessage("HelpPopup.PageTitle", null, locale);
        String MothusExplanation = messageSource.getMessage("HelpPopup.MothusExplanation", null, locale);
        String RulesExplanation = messageSource.getMessage("HelpPopup.RulesExplanation", null, locale);
        String LettersExplanation = messageSource.getMessage("HelpPopup.LettersExplanation", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("MothusExplanation", MothusExplanation);
        model.addAttribute("RulesExplanation", RulesExplanation);
        model.addAttribute("LettersExplanation", LettersExplanation);

        return "helpPopup";
    }
}