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
 * This class is used to manage the shop zone.
 */
@Controller
public class ManageShopZone {
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
    public ManageShopZone(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * This method is used to show the shop zone.
     * It adds the texts to the model from locale.
     * @param model the model
     * @param locale the locale
     * @return the shop zone page
     */
    @GetMapping("/shopZone")
    public String shopZone(Model model, Locale locale, Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }

        String pageTitle = messageSource.getMessage("ShopZone.PageTitle", null, locale);
        String menuCoinShop = messageSource.getMessage("ShopZone.MenuCoinShop", null, locale);
        String menuElementCase = messageSource.getMessage("ShopZone.MenuElementCase", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("menuCoinShop", menuCoinShop);
        model.addAttribute("menuElementCase", menuElementCase);

        return "shopZone";
    }

    /**
     * This method is used to show the coin shop content.
     * @param model the model
     * @return the coin shop content page
     */
    @GetMapping("/coinShopContent")
    public String loadCoinShopContent(Model model) {
        return "Content/coinShopContent";
    }

    /**
     * This method is used to show the element case content.
     * @param model the model
     * @return the element case content page
     */
    @GetMapping("/elementCaseContent")
    public String loadElementCaseContent(Model model) {
        return "Content/elementCaseContent";
    }

    /**
     * This method is used to show the credit card popup.
     * It adds the texts to the model from locale.
     * @param model the model
     * @param locale the locale
     * @return the credit card popup page
     */
    @GetMapping("/creditCardPopup")
    public String creditCardPopup(Model model, Locale locale, Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }

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