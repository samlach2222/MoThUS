package com.siesth.mothus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class ManageAccountZone {
    private final MessageSource messageSource;

    @Autowired
    public ManageAccountZone(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/accountZone")
    public String accountZone(Model model, Locale locale) {
        String pageTitle = messageSource.getMessage("AccountZone.PageTitle", null, locale);
        String menuAccount = messageSource.getMessage("AccountZone.MenuAccount", null, locale);
        String menuElementSkins = messageSource.getMessage("AccountZone.MenuElementSkins", null, locale);
        String menuPageSkins = messageSource.getMessage("AccountZone.MenuPageSkins", null, locale);
        String menuTermsOfUse = messageSource.getMessage("AccountZone.MenuTermsOfUse", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("menuAccount", menuAccount);
        model.addAttribute("menuElementSkins", menuElementSkins);
        model.addAttribute("menuPageSkins", menuPageSkins);
        model.addAttribute("menuTermsOfUse", menuTermsOfUse);

        return "accountZone";
    }

    @GetMapping("/accountContent")
    public String loadAccountContent(Model model) {
        // To pass data to the template
        model.addAttribute("email", "email@test.com");
        model.addAttribute("username","joemama");
        return "Content/accountContent"; // Thymeleaf template name
    }

    @GetMapping("/elementSkinsContent")
    public String loadElementSkinsContent(Model model) {
        // To pass data to the template
        model.addAttribute("someData", "Some data for Coin Shop");
        return "Content/elementSkinsContent"; // Thymeleaf template name
    }

    @GetMapping("/pageSkinsContent")
    public String loadPageSkinsContent(Model model) {
        // To pass data to the template
        model.addAttribute("someData", "Some data for Coin Shop");
        return "Content/pageSkinsContent"; // Thymeleaf template name
    }

    @GetMapping("/termsOfUseContent")
    public String loadTermsOfUseContent(Model model) {
        // To pass data to the template
        model.addAttribute("someData", "Some data for Coin Shop");
        return "Content/termsOfUseContent"; // Thymeleaf template name
    }
}