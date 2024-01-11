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
        return "Content/coinShopContent";
    }

    @GetMapping("/elementCaseContent")
    public String loadElementCaseContent(Model model) {
        return "Content/elementCaseContent";
    }

    @GetMapping("/creditCardPopup")
    public String creditCardPopup(Model model, Locale locale) {

        // locale BEGIN
        String paymentAmountLabel = messageSource.getMessage("ShopZone.CreditCardPopup.PaymentAmountLabel", null, locale);
        String payInvoiceLabel = messageSource.getMessage("ShopZone.CreditCardPopup.PayInvoiceLabel", null, locale);
        String nameOnCardLabel = messageSource.getMessage("ShopZone.CreditCardPopup.NameOnCardLabel", null, locale);
        String namePlaceholder = messageSource.getMessage("ShopZone.CreditCardPopup.NamePlaceholder", null, locale);
        String cardNumberLabel = messageSource.getMessage("ShopZone.CreditCardPopup.CardNumberLabel", null, locale);
        String expirationDateLabel = messageSource.getMessage("ShopZone.CreditCardPopup.ExpirationDateLabel", null, locale);
        String securityCodeLabel = messageSource.getMessage("ShopZone.CreditCardPopup.SecurityCodeLabel", null, locale);
        String zipCodeLabel = messageSource.getMessage("ShopZone.CreditCardPopup.ZipCodeLabel", null, locale);
        String payButton = messageSource.getMessage("ShopZone.CreditCardPopup.PayButton", null, locale);
        String cancelButton = messageSource.getMessage("ShopZone.CreditCardPopup.CancelButton", null, locale);

        model.addAttribute("paymentAmountLabel", paymentAmountLabel);
        model.addAttribute("payInvoiceLabel", payInvoiceLabel);
        model.addAttribute("nameOnCardLabel", nameOnCardLabel);
        model.addAttribute("namePlaceholder", namePlaceholder);
        model.addAttribute("cardNumberLabel", cardNumberLabel);
        model.addAttribute("expirationDateLabel", expirationDateLabel);
        model.addAttribute("securityCodeLabel", securityCodeLabel);
        model.addAttribute("zipCodeLabel", zipCodeLabel);
        model.addAttribute("payButton", payButton);
        model.addAttribute("cancelButton", cancelButton);
        // locale END

        return "Popup/creditCardPopup";
    }
}