package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IGameManagement;
import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.model.Game;
import com.siesth.mothus.model.Skin;
import com.siesth.mothus.model.UserLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Locale;

/**
 * This class is used to manage the play zone.
 */
@Controller
public class ManagePlayZone {
    /**
     * This field is used to get the message from the message source.
     */
    private final MessageSource messageSource;

    /**
     * This field is used to get the resource from the resource loader.
     */
    private final ResourceLoader resourceLoader;

    /**
     * This field is used to store the word of the day.
     */
    private String frenchWord;
    private String englishWord;

    /**
     * This field is used to get the game manager.
     */
    @Autowired
    private IGameManagement gameManager;

    @Autowired
    private IUserManagement userManagement;

    /**
     * This constructor is used to autowire the message source and resource loader.
     * @param messageSource  the message source
     * @param resourceLoader the resource loader
     */
    @Autowired
    public ManagePlayZone(MessageSource messageSource, ResourceLoader resourceLoader) {
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
    }

    /**
     * This method is used to get the today's word.
     * It adds the texts to the model from locale.
     * @return the today's word
     */
    @GetMapping("/getTodayWordData")
    @ResponseBody
    public String getTodayWordData(Authentication authentication, Locale locale) {
        String[] letters;

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            // Fetch the latest game entity
            Game latestGame = gameManager.getTodayGame();

            letters = switch (userManagement.getLanguageByUsername(currentUserName)) {
                case fr -> {
                    // Extract the word from the latest game entity
                    frenchWord = latestGame.getFrenchWord();
                    // split each upper case letter
                    yield frenchWord.split("(?=[A-Z])");
                }
                // Default language is english
                default -> {
                    // Extract the word from the latest game entity
                    englishWord = latestGame.getEnglishWord();
                    // split each upper case letter
                    yield englishWord.split("(?=[A-Z])");  // Default language is english
                }
            };
        } else {
            letters = switch (UserLanguage.fromLocaleOrEn(locale)) {
                case fr -> {
                    // Extract the word from the latest game entity
                    frenchWord = gameManager.getRandomWord(UserLanguage.fr);
                    // split each upper case letter
                    yield frenchWord.split("(?=[A-Z])");
                }
                // Default language is english
                default -> {
                    // Extract the word from the latest game entity
                    englishWord = gameManager.getRandomWord(UserLanguage.en);
                    // split each upper case letter
                    yield englishWord.split("(?=[A-Z])");  // Default language is english
                }
            };
        }

        int length = letters.length;
        String firstLetter = letters[0];

        // return length and first letter
        return length + " " + firstLetter;
    }

    /**
     * This method is used to compare the received word with the today's word.
     * @param receivedWord the received word
     * @return the result of the comparison
     */
    private String compareWords(String receivedWord, Locale locale) {
        // remove empty strings and quote from receivedWord
        receivedWord = receivedWord.replace("\"", "");
        receivedWord = receivedWord.replace(" ", "");
        UserLanguage userLanguage;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            userLanguage = userManagement.getLanguageByUsername(currentUserName);
        } else {
            userLanguage = UserLanguage.fromLocaleOrEn(locale);
        }
        String[] localWordLetters = switch (userLanguage) {
            case fr -> frenchWord.split("(?=[A-Z])");
            // Default language is english
            default -> englishWord.split("(?=[A-Z])");
        };
        String[] receivedWordLetters = receivedWord.split("(?=[A-Z])");

        String[] result = new String[receivedWordLetters.length];

        // check red color
        for (int i = 0; i < receivedWordLetters.length; i++) {
            if (receivedWordLetters[i].equals(localWordLetters[i])) {
                result[i] = "+";
                receivedWordLetters[i] = "";
                localWordLetters[i] = "";
            }
        }

        // check yellow color
        for (int i = 0; i < receivedWordLetters.length; i++) {
            if (!receivedWordLetters[i].isEmpty()) {
                // check if not contains
                for (int j = 0; j < localWordLetters.length; j++) {
                    String localWordLetter = localWordLetters[j];
                    if (localWordLetter.equals(receivedWordLetters[i])) {
                        result[i] = "*";
                        receivedWordLetters[i] = "";
                        localWordLetters[j] = "";
                        break;
                    }
                }
            }
        }

        // check purple color
        for (int i = 0; i < receivedWordLetters.length; i++) {

            String receivedWordLetter = receivedWordLetters[i].toLowerCase();
            String localWordLetter = localWordLetters[i].toLowerCase();

            // check if one of the strings is size 2
            if (receivedWordLetter.length() == 2) {
                for (int j = 0; j < receivedWordLetter.length(); j++) {
                    if (localWordLetter.contains(receivedWordLetter.charAt(j) + "")) {
                        result[i] = "/";
                        receivedWordLetters[i] = "";
                        localWordLetters[i] = "";
                        break;
                    }
                }
            } else {
                for (int j = 0; j < localWordLetter.length(); j++) {
                    if (receivedWordLetter.contains(localWordLetter.charAt(j) + "")) {
                        result[i] = "/";
                        receivedWordLetters[i] = "";
                        localWordLetters[i] = "";
                        break;
                    }
                }
            }
        }

        // check blue color
        for (int i = 0; i < receivedWordLetters.length; i++) {
            if (!receivedWordLetters[i].isEmpty()) {
                // check if not contains
                switch (userLanguage) {
                    case fr -> {
                        if (!frenchWord.contains(receivedWordLetters[i])) {
                            result[i] = "-";
                            receivedWordLetters[i] = "";
                            localWordLetters[i] = "";
                        }
                    }
                    // Default language is english
                    default -> {
                        if (!englishWord.contains(receivedWordLetters[i])) {
                            result[i] = "-";
                            receivedWordLetters[i] = "";
                            localWordLetters[i] = "";
                        }
                    }
                }
            }
        }

        // build final string
        StringBuilder resultString = new StringBuilder();
        for (String s : result) {
            if (s == null) {
                s = "-";
            }
            resultString.append(s);
        }
        return resultString.toString();
    }

    /**
     * This method is used to show the play zone.
     * It adds the texts to the model from locale.
     * @param model  the model
     * @param locale the locale
     * @return the play zone page
     */
    @GetMapping("/playZone")
    public String playZone(Model model, Locale locale, Authentication authentication) {
        if (authentication != null) {
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                String currentUserName = authentication.getName();
                locale = userManagement.getLanguageByUsername(currentUserName).toLocale();
                int pageSkinId = userManagement.getSkinInventoryByUsername(currentUserName).getCurrentPageSkinId();
                int elementSkinId = userManagement.getSkinInventoryByUsername(currentUserName).getCurrentElementSkinId();
                Collection<Skin> skinList = userManagement.getSkinInventoryByUsername(currentUserName).getSkinList();
                for (Skin s : skinList) {
                    if (s.getIdSkin() == pageSkinId) {
                        model.addAttribute("pageSkin", s.getCssFile());
                    }
                    if (s.getIdSkin() == elementSkinId) {
                        model.addAttribute("elementSkin", s.getCssFile());
                    }
                }
            }
        }

        String pageTitle = messageSource.getMessage("PlayZone.PageTitle", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        return "playZone";
    }

    /**
     * This method is used to get elements data from the yaml file.
     * @return the elements data
     */
    @GetMapping("/getYamlData")
    @ResponseBody
    public String getYamlData() throws IOException {
        Resource file = resourceLoader.getResource("classpath:static/assets/elements.yml");
        return file.getContentAsString(StandardCharsets.UTF_8);
    }

    /**
     * This method is used to receive the word from the client.
     * @param word the received word
     * @return the result of the comparison
     */
    @PostMapping("/sendWord")
    @ResponseBody
    public String receiveWord(@RequestBody String word, Locale locale) {
        System.out.println("Received word: " + word);
        return compareWords(word, locale);
    }

    @PostMapping("/getMollards")
    @ResponseBody
    public int getMollards(Authentication authentication) {
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return userManagement.getMollardsByUsername(currentUserName);
        } else {
            return 0;
        }
    }

    @PostMapping("/getMessageUncompletedLine")
    @ResponseBody
    public String getMessageUncompletedLine(Authentication authentication) {
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Locale locale = userManagement.getLanguageByUsername(currentUserName).toLocale();
            return messageSource.getMessage("PlayZone.UncompletedLineMessage", null, locale);
        } else {
            return null;
        }
    }

    @PostMapping("/getMessageWin")
    @ResponseBody
    public String getMessageWin(Authentication authentication) {
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Locale locale = userManagement.getLanguageByUsername(currentUserName).toLocale();
            return messageSource.getMessage("PlayZone.WinMessage", null, locale);
        } else {
            return null;
        }
    }

    @PostMapping("/getMessageLoose")
    @ResponseBody
    public String getMessageLoose(Authentication authentication) {
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Locale locale = userManagement.getLanguageByUsername(currentUserName).toLocale();
            return messageSource.getMessage("PlayZone.LooseMessage", null, locale);
        } else {
            return null;
        }
    }

    @PostMapping("/getMessageClipboard")
    @ResponseBody
    public String getMessageClipboard(Authentication authentication) {
        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Locale locale = userManagement.getLanguageByUsername(currentUserName).toLocale();
            return messageSource.getMessage("PlayZone.ClipboardMessage", null, locale);
        } else {
            return null;
        }
    }
}