package com.siesth.mothus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class ManageShopZone {
    private final MessageSource messageSource;

    @Autowired
    public ManageShopZone(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/shopZone")
    public String shopZone(Model model, Locale locale) {
        String pageTitle = messageSource.getMessage("ShopZone.PageTitle", null, locale);
        String menuCoinShop = messageSource.getMessage("ShopZone.MenuCoinShop", null, locale);
        String menuElementCase = messageSource.getMessage("ShopZone.MenuElementCase", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("menuCoinShop", menuCoinShop);
        model.addAttribute("menuElementCase", menuElementCase);

        return "shopZone";
    }

    @GetMapping("/coinShopContent")
    public String loadCoinShopContent(Model model) {
        // To pass data to the template
        model.addAttribute("someData", "Some data for Coin Shop");
        return "Content/coinShopContent"; // Thymeleaf template name
    }

    @GetMapping("/elementCaseContent")
    public String loadElementCaseContent(Model model) {
        // o pass data to the template
        model.addAttribute("someData", "Some data for Coin Shop");
        return "Content/elementCaseContent"; // Thymeleaf template name
    }

    @GetMapping("/creditCardPopup")
    public String creditCardPopup(Model model, Locale locale) {
        return "Popup/creditCardPopup";
    }
}