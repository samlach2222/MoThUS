package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.model.Skin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String loadElementCaseContent(Model model, Authentication authentication, Locale locale){
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }

        // locale BEGIN
        String buyCommonCase = messageSource.getMessage("ShopZone.ElementCaseContent.BuyCommonCase", null, locale);
        String buyRareCase = messageSource.getMessage("ShopZone.ElementCaseContent.BuyRareCase", null, locale);
        String buyMythicCase = messageSource.getMessage("ShopZone.ElementCaseContent.BuyMythicCase", null, locale);
        String commonCaseMollards = messageSource.getMessage("ShopZone.ElementCaseContent.CommonCaseMollards", null, locale);
        String rareCaseMollards = messageSource.getMessage("ShopZone.ElementCaseContent.RareCaseMollards", null, locale);
        String mythicCaseMollards = messageSource.getMessage("ShopZone.ElementCaseContent.MythicCaseMollards", null, locale);
        String chances = messageSource.getMessage("ShopZone.ElementCaseContent.Chances", null, locale);
        String commonSkin55 = messageSource.getMessage("ShopZone.ElementCaseContent.CommonSkin55", null, locale);
        String uncommonSkin30 = messageSource.getMessage("ShopZone.ElementCaseContent.UncommonSkin30", null, locale);
        String rareSkin15 = messageSource.getMessage("ShopZone.ElementCaseContent.RareSkin15", null, locale);
        String mythicSkin5 = messageSource.getMessage("ShopZone.ElementCaseContent.MythicSkin5", null, locale);
        String commonSkin30 = messageSource.getMessage("ShopZone.ElementCaseContent.CommonSkin30", null, locale);
        String rareSkin25 = messageSource.getMessage("ShopZone.ElementCaseContent.RareSkin25", null, locale);
        String mythicSkin15 = messageSource.getMessage("ShopZone.ElementCaseContent.MythicSkin15", null, locale);
        String commonSkin10 = messageSource.getMessage("ShopZone.ElementCaseContent.CommonSkin10", null, locale);
        String uncommonSkin10 = messageSource.getMessage("ShopZone.ElementCaseContent.UncommonSkin10", null, locale);
        String rareSkin40 = messageSource.getMessage("ShopZone.ElementCaseContent.RareSkin40", null, locale);
        String mythicSkin40 = messageSource.getMessage("ShopZone.ElementCaseContent.MythicSkin40", null, locale);

        model.addAttribute("buyCommonCase", buyCommonCase);
        model.addAttribute("buyRareCase", buyRareCase);
        model.addAttribute("buyMythicCase", buyMythicCase);
        model.addAttribute("commonCaseMollards", commonCaseMollards);
        model.addAttribute("rareCaseMollards", rareCaseMollards);
        model.addAttribute("mythicCaseMollards", mythicCaseMollards);
        model.addAttribute("chances", chances);
        model.addAttribute("commonSkin55", commonSkin55);
        model.addAttribute("uncommonSkin30", uncommonSkin30);
        model.addAttribute("rareSkin15", rareSkin15);
        model.addAttribute("mythicSkin5", mythicSkin5);
        model.addAttribute("commonSkin30", commonSkin30);
        model.addAttribute("rareSkin25", rareSkin25);
        model.addAttribute("mythicSkin15", mythicSkin15);
        model.addAttribute("commonSkin10", commonSkin10);
        model.addAttribute("uncommonSkin10", uncommonSkin10);
        model.addAttribute("rareSkin40", rareSkin40);
        model.addAttribute("mythicSkin40", mythicSkin40);
        // locale END

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
    public String creditCardPopup(Model model, Locale locale, Authentication authentication, @RequestParam(name = "lootboxType", required = true) String lootboxType) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }

        switch(lootboxType){
            case "commonBuy":
                model.addAttribute("coinNumber", 100);
                model.addAttribute("lootboxPrice", "1€99");
                break;
            case "rareBuy":
                model.addAttribute("coinNumber", 500);
                model.addAttribute("lootboxPrice", "8€99");
                break;
            case "mythicBuy":
                model.addAttribute("coinNumber", 1000);
                model.addAttribute("lootboxPrice", "15€99");
                break;
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

    @PostMapping("/payMollard")
    @ResponseBody
    public String payMollard(Authentication authentication, @RequestBody Map<String, String> mollardInfo) {
        int amount = Integer.parseInt(mollardInfo.get("amount"));
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            userManagement.addBalanceByUsername(currentUserName, amount);
            return "OK";
        }
        else {
            return "You are not logged in!"; // TODO: translate
        }
    }
}