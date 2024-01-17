package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.model.Skin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
import java.util.Map;

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
    public String loadCoinShopContent(Model model, Authentication authentication, Locale locale) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
            int mollardValue = userManagement.getMollardsByUsername(currentUserName);
            model.addAttribute("mollardValue", mollardValue);
        }

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

    @PostMapping("/getRandomSkin")
    @ResponseBody
    public String getRandomSkin(Authentication authentication, @RequestBody Map<String, String> boxInfo) {
        String type = boxInfo.get("type");
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            int commonProb = 0;
            int uncommonProb = 0;
            int rareProb = 0;
            int mythicProb = 0;
            switch (type) {
                case "Common":
                    //55 30 15 5
                    commonProb = 55;
                    uncommonProb = 30;
                    rareProb = 15;
                    mythicProb = 5;
                case "Rare":
                    // 30 30 25 15
                    commonProb = 30;
                    uncommonProb = 30;
                    rareProb = 25;
                    mythicProb = 15;
                case "Mythic":
                    // 10 10 40 40
                    commonProb = 10;
                    uncommonProb = 10;
                    rareProb = 40;
                    mythicProb = 40;
            }
            int random = (int) (Math.random() * 100);
            String rarity;
            if (random < commonProb) {
                rarity = "Common";
            } else if (random < commonProb + uncommonProb) {
                rarity = "Uncommon";
            } else if (random < commonProb + uncommonProb + rareProb) {
                rarity = "Rare";
            } else {
                rarity = "Mythic";
            }
            Skin s = userManagement.getRandomSkin(currentUserName, rarity);
            // transform to json
            return "{\"type\":\"" + s.getType() + "\",\"rarity\":\"" + s.getRarity() + "\",\"cssPath\":\"" + s.getCssFile() + "\",\"imagePath\":\"" + s.getPreviewImage() + "\"}";
        }
        else {
            return null;
        }
    }
}