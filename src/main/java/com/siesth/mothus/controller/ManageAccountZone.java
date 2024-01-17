package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.model.Skin;
import com.siesth.mothus.model.SkinInventory;
import com.siesth.mothus.model.SkinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * This class is used to manage the account zone.
 * The account zone is the zone where the user can manage his account.
 */
@Controller
public class ManageAccountZone {
    /**
     * The message source is used to get the messages from the messages.properties file.
     */
    private final MessageSource messageSource;

    /**
     * The user management is used to manage the user.
     */
    @Autowired
    private IUserManagement userManagement;

    /**
     * This constructor is used to autowire the message source.
     * @param messageSource the message source
     */
    @Autowired
    public ManageAccountZone(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * This method is used to load the account zone.
     * It adds the texts to the model from locale.
     * @param model the model
     * @param locale the locale
     * @return the account zone
     */
    @GetMapping("/accountZone")
    public String accountZone(Model model, Locale locale, Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }

        // locale BEGIN
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
        // locale END

        return "accountZone";
    }

    /**
     * This method is used to load the account content.
     * It adds the texts to the model from locale and user information.
     * @param model the model
     * @param locale the locale
     * @return the account content
     */
    @GetMapping("/accountContent")
    public String loadAccountContent(Model model, Locale locale, Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }

        // locale BEGIN
        String pageTitle = messageSource.getMessage("AccountZone.AccountContent.PageTitle", null, locale);
        String accountTitle = messageSource.getMessage("AccountZone.AccountContent.AccountTitle", null, locale);
        String emailTitle = messageSource.getMessage("AccountZone.AccountContent.EmailTitle", null, locale);
        String usernameTitle = messageSource.getMessage("AccountZone.AccountContent.UsernameTitle", null, locale);
        String passwordTitle = messageSource.getMessage("AccountZone.AccountContent.PasswordTitle", null, locale);
        String languageTitle = messageSource.getMessage("AccountZone.AccountContent.LanguageTitle", null, locale);
        String emailSubtitle = messageSource.getMessage("AccountZone.AccountContent.EmailSubtitle", null, locale);
        String usernameSubtitle = messageSource.getMessage("AccountZone.AccountContent.UsernameSubtitle", null, locale);
        String passwordSubtitle = messageSource.getMessage("AccountZone.AccountContent.PasswordSubtitle", null, locale);
        String languageSubtitle = messageSource.getMessage("AccountZone.AccountContent.LanguageSubtitle", null, locale);
        String resetPasswordButton = messageSource.getMessage("AccountZone.AccountContent.ResetPasswordButton", null, locale);
        String logOutButton = messageSource.getMessage("AccountZone.AccountContent.LogOutButton", null, locale);
        String previousPasswordLabel = messageSource.getMessage("AccountZone.AccountContent.PreviousPasswordLabel", null, locale);
        String newPasswordLabel = messageSource.getMessage("AccountZone.AccountContent.NewPasswordLabel", null, locale);
        String confirmNewPasswordLabel = messageSource.getMessage("AccountZone.AccountContent.ConfirmNewPasswordLabel", null, locale);
        String validateButton = messageSource.getMessage("AccountZone.AccountContent.ValidateButton", null, locale);
        String cancelButton = messageSource.getMessage("AccountZone.AccountContent.CancelButton", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("accountTitle", accountTitle);
        model.addAttribute("emailTitle", emailTitle);
        model.addAttribute("usernameTitle", usernameTitle);
        model.addAttribute("passwordTitle", passwordTitle);
        model.addAttribute("languageTitle", languageTitle);
        model.addAttribute("emailSubtitle", emailSubtitle);
        model.addAttribute("usernameSubtitle", usernameSubtitle);
        model.addAttribute("passwordSubtitle", passwordSubtitle);
        model.addAttribute("languageSubtitle", languageSubtitle);
        model.addAttribute("resetPasswordButton", resetPasswordButton);
        model.addAttribute("logOutButton", logOutButton);
        model.addAttribute("previousPasswordLabel", previousPasswordLabel);
        model.addAttribute("newPasswordLabel", newPasswordLabel);
        model.addAttribute("confirmNewPasswordLabel", confirmNewPasswordLabel);
        model.addAttribute("validateButton", validateButton);
        model.addAttribute("cancelButton", cancelButton);
        // locale END

        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            model.addAttribute("username", currentUserName);
            model.addAttribute("email", userManagement.getEmailByUsername(currentUserName));
            model.addAttribute("language", userManagement.getLanguageByUsername(currentUserName));
            model.addAttribute("englishLanguageSelected", userManagement.getLanguageByUsername(currentUserName).equals("en"));
            model.addAttribute("frenchLanguageSelected", userManagement.getLanguageByUsername(currentUserName).equals("fr"));
        }

        return "Content/accountContent";
    }

    /**
     * This method is used to load the element skins content.
     * It adds the texts to the model from locale.
     * @param model the model
     * @param locale the locale
     * @return the element skins content
     */
    @GetMapping("/elementSkinsContent")
    public String loadElementSkinsContent(Model model, Locale locale, Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }

        // locale BEGIN
        String pageTitle = messageSource.getMessage("AccountZone.ElementSkinsContent.PageTitle", null, locale);
        String equippedLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.EquippedLabel", null, locale);
        String commonLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.CommonLabel", null, locale);
        String uncommonLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.UncommonLabel", null, locale);
        String rareLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.RareLabel", null, locale);
        String mythicLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.MythicLabel", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("equippedLabel", equippedLabel);
        model.addAttribute("commonLabel", commonLabel);
        model.addAttribute("uncommonLabel", uncommonLabel);
        model.addAttribute("rareLabel", rareLabel);
        model.addAttribute("mythicLabel", mythicLabel);
        // locale END

        return "Content/elementSkinsContent";
    }

    /**
     * This method is used to load the page skins content.
     * It adds the texts to the model from locale.
     * @param model the model
     * @param locale the locale
     * @return the page skins content
     */
    @GetMapping("/pageSkinsContent")
    public String loadPageSkinsContent(Model model, Locale locale, Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }
        // locale BEGIN
        String pageTitle = messageSource.getMessage("AccountZone.ElementSkinsContent.PageTitle", null, locale);
        String equippedLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.EquippedLabel", null, locale);
        String commonLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.CommonLabel", null, locale);
        String uncommonLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.UncommonLabel", null, locale);
        String rareLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.RareLabel", null, locale);
        String mythicLabel = messageSource.getMessage("AccountZone.ElementSkinsContent.MythicLabel", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("equippedLabel", equippedLabel);
        model.addAttribute("commonLabel", commonLabel);
        model.addAttribute("uncommonLabel", uncommonLabel);
        model.addAttribute("rareLabel", rareLabel);
        model.addAttribute("mythicLabel", mythicLabel);
        // locale END
        return "Content/pageSkinsContent";
    }

    /**
     * This method is used to get page skins data for the current User.
     * @return the page skin data
     */
    @GetMapping("/getPageSkinsData")
    @ResponseBody
    public String getPageSkinsData(Authentication authentication) {
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            SkinInventory skinInventory = userManagement.getSkinInventoryByUsername(currentUserName);
            int equippedSkinId = skinInventory.getCurrentPageSkinId();
            // get all skins
            Collection<Skin> skins = skinInventory.getSkinList();
            ArrayList<Skin> pageSkins = new ArrayList<>();
            for(Skin skin : skins) {
                if (skin.getType() == SkinType.PageSkin) {
                    pageSkins.add(skin);
                }
            }
            // jsonify the array
            StringBuilder result = new StringBuilder();
            result.append("[");
            for (Skin skin : pageSkins) {
                result.append("{");
                result.append("\"rarity\": \"").append(skin.getRarity().toString()).append("\",");
                result.append("\"cssFile\": \"").append(skin.getCssFile()).append("\",");
                result.append("\"previewImage\": \"").append(skin.getPreviewImage()).append("\",");
                if(skin.getIdSkin() == equippedSkinId) {
                    result.append("\"equipped\": true");
                }
                else {
                    result.append("\"equipped\": false");
                }
                result.append("},");
            }
            result.deleteCharAt(result.length() - 1);
            result.append("]");
            return result.toString();
        }
        return null;
    }

    /**
     * This method is used to get page skins data for the current User.
     * @return the page skin data
     */
    @GetMapping("/getElementSkinsData")
    @ResponseBody
    public String getElementSkinsData(Authentication authentication) {
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            SkinInventory skinInventory = userManagement.getSkinInventoryByUsername(currentUserName);
            int equippedSkinId = skinInventory.getCurrentElementSkinId();
            // get all skins
            Collection<Skin> skins = skinInventory.getSkinList();
            ArrayList<Skin> elementSkins = new ArrayList<>();
            for(Skin skin : skins) {
                if (skin.getType() == SkinType.ElementSkin) {
                    elementSkins.add(skin);
                }
            }
            // jsonify the array
            StringBuilder result = new StringBuilder();
            result.append("[");
            for (Skin skin : elementSkins) {
                result.append("{");
                result.append("\"rarity\": \"").append(skin.getRarity().toString()).append("\",");
                result.append("\"cssFile\": \"").append(skin.getCssFile()).append("\",");
                result.append("\"previewImage\": \"").append(skin.getPreviewImage()).append("\",");
                if(skin.getIdSkin() == equippedSkinId) {
                    result.append("\"equipped\": true");
                }
                else {
                    result.append("\"equipped\": false");
                }
                result.append("},");
            }
            result.deleteCharAt(result.length() - 1);
            result.append("]");
            return result.toString();
        }
        return null;
    }

    /**
     * This method is used to change the password.
     * @param authentication the authentication
     * @param passwordChangeRequest data from the form
     * @return a message
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public String changePassword(Authentication authentication, Locale locale, @RequestBody Map<String, String> passwordChangeRequest) {
        String previousPassword = passwordChangeRequest.get("oldPassword");
        String newPassword = passwordChangeRequest.get("newPassword");
        String confirmNewPassword = passwordChangeRequest.get("newPasswordConfirm");
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
            String serverPass = userManagement.getPasswordByUsername(currentUserName);
            Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
            if(arg2SpringSecurity.matches(previousPassword, serverPass)) {
                if(newPassword.equals(confirmNewPassword)) {
                    userManagement.updatePasswordByUsername(currentUserName, newPassword);
                    return "SUCCESS"; // not translate this
                }
                else {
                    return messageSource.getMessage("AccountZone.ChangePassword.PassAndConfirmNotEquals", null, locale);
                }
            }
            else {
                return messageSource.getMessage("AccountZone.ChangePassword.OldPasswordWrong", null, locale);
            }
        }
        else {
            return messageSource.getMessage("AccountZone.ChangePassword.NotLoggedIn", null, locale);
        }
    }

    @PostMapping("/changeUsername")
    @ResponseBody
    public String changeUsername(Authentication authentication, Locale locale, @RequestBody Map<String, String> usernameChangeRequest) {
        String newUsername = usernameChangeRequest.get("newUsername");
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
            if(userManagement.isUsernameTaken(newUsername)) {
                return messageSource.getMessage("AccountZone.ChangeUsername.UsernameAlreadyTaken", null, locale);
            }
            else {
                userManagement.updateUsernameByUsername(currentUserName, newUsername);
                return "SUCCESS"; // not translate this
            }
        }
        else {
            return messageSource.getMessage("AccountZone.ChangeUsername.NotLoggedIn", null, locale);
        }
    }

    @PostMapping("/changeMail")
    @ResponseBody
    public String changeMail(Authentication authentication, Locale locale, @RequestBody Map<String, String> mailChangeRequest) {
        String newMail = mailChangeRequest.get("newMail");
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
            if(userManagement.isMailTaken(newMail)) {
                return messageSource.getMessage("AccountZone.ChangeMail.MailAlreadyTaken", null, locale);
            }
            else {
                userManagement.updateEmailByUsername(currentUserName, newMail);
                return "SUCCESS"; // not translate this
            }
        }
        else {
            return messageSource.getMessage("AccountZone.ChangeMail.NotLoggedIn", null, locale);
        }
    }

    @PostMapping("/changeEquippedSkin")
    @ResponseBody
    public String changeEquippedSkin(Authentication authentication, @RequestBody Map<String, String> skinChangeRequest) {
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String cssFile = skinChangeRequest.get("cssFile");

            SkinInventory skinInventory = userManagement.getSkinInventoryByUsername(currentUserName);
            Skin skin = skinInventory.getSkinList().stream().filter(s -> s.getCssFile().equals(cssFile)).findFirst().orElse(null);

            if(skin != null) {
                if(skin.getType() == SkinType.ElementSkin) {
                    skinInventory.setCurrentElementSkinId(skin.getIdSkin());
                }
                else if(skin.getType() == SkinType.PageSkin) {
                    skinInventory.setCurrentPageSkinId(skin.getIdSkin());
                }
                userManagement.updateSkinInventoryByUsername(currentUserName, skinInventory);
                return "OK";
            }
            else {
                return "NOTOK";
            }
        }
        else {
            return "NOTOK";
        }
    }

    /**
     * This method is used to load the terms of use content.
     * It adds the texts to the model from locale.
     * @param model the model
     * @param locale the locale
     * @return the terms of use content
     */
    @GetMapping("/termsOfUseContent")
    public String loadTermsOfUseContent(Model model, Locale locale, Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }

        // locale BEGIN
        String pageTitle = messageSource.getMessage("AccountZone.TermsOfUseContent.PageTitle", null, locale);
        String title1 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title1", null, locale);
        String title1Par = messageSource.getMessage("AccountZone.TermsOfUseContent.Title1Par", null, locale);
        String title2 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title2", null, locale);
        String title2Dot1 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title2Dot1", null, locale);
        String title2Dot2 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title2Dot2", null, locale);
        String title2Dot3 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title2Dot3", null, locale);
        String title3 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title3", null, locale);
        String title3Dot1 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title3Dot1", null, locale);
        String title3Dot2 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title3Dot2", null, locale);
        String title4 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title4", null, locale);
        String title4Dot1 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title4Dot1", null, locale);
        String title4Dot2 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title4Dot2", null, locale);
        String title5 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title5", null, locale);
        String title5Dot1 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title5Dot1", null, locale);
        String title5Dot2 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title5Dot2", null, locale);
        String title6 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title6", null, locale);
        String title6Dot1 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title6Dot1", null, locale);
        String title7 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title7", null, locale);
        String title7Dot1 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title7Dot1", null, locale);
        String title8 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title8", null, locale);
        String title8Dot1 = messageSource.getMessage("AccountZone.TermsOfUseContent.Title8Dot1", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("title1", title1);
        model.addAttribute("title1Par", title1Par);
        model.addAttribute("title2", title2);
        model.addAttribute("title2Dot1", title2Dot1);
        model.addAttribute("title2Dot2", title2Dot2);
        model.addAttribute("title2Dot3", title2Dot3);
        model.addAttribute("title3", title3);
        model.addAttribute("title3Dot1", title3Dot1);
        model.addAttribute("title3Dot2", title3Dot2);
        model.addAttribute("title4", title4);
        model.addAttribute("title4Dot1", title4Dot1);
        model.addAttribute("title4Dot2", title4Dot2);
        model.addAttribute("title5", title5);
        model.addAttribute("title5Dot1", title5Dot1);
        model.addAttribute("title5Dot2", title5Dot2);
        model.addAttribute("title6", title6);
        model.addAttribute("title6Dot1", title6Dot1);
        model.addAttribute("title7", title7);
        model.addAttribute("title7Dot1", title7Dot1);
        model.addAttribute("title8", title8);
        model.addAttribute("title8Dot1", title8Dot1);
        // locale END

        return "Content/termsOfUseContent";
    }

    @PostMapping("/changeLocale")
    public String changeLocale(@RequestParam("language") String language) {
        // get current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            userManagement.updateLanguageByUsername(currentUserName, language);
        }
        return "redirect:/accountZone";
    }
}