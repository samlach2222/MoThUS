package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IGameManagement;
import com.siesth.mothus.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Controller
public class ManagePlayZone {
    private final MessageSource messageSource;
    private final ResourceLoader resourceLoader;
    private String frenchWord;
    @Autowired
    private IGameManagement gameManager;

    @Autowired
    public ManagePlayZone(MessageSource messageSource, ResourceLoader resourceLoader) {
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/getTodayWordData")
    @ResponseBody
    public String getTodayWordData() throws IOException {
        // Fetch the latest game entity
        Game latestGame = gameManager.getTodayGame();
        // Extract the word from the latest game entity
        frenchWord = latestGame.getFrenchWord(); // TODO : When accounts are made, get the user's language and fetch the word in the user's language

        // split each upper case letter
        String[] letters = frenchWord.split("(?=[A-Z])");
        int length = letters.length;
        String firstLetter = letters[0];

        // return length and first letter
        return length + " " + firstLetter;
    }

    private String compareWords(String receivedWord) {
        // remove empty strings and quote from receivedWord
        receivedWord = receivedWord.replace("\"", "");
        receivedWord = receivedWord.replace(" ", "");
        String[] localWordLetters = frenchWord.split("(?=[A-Z])");
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
            if(receivedWordLetter.length() == 2) {
                for (int j = 0; j < receivedWordLetter.length(); j++) {
                    if (localWordLetter.contains(receivedWordLetter.charAt(j) + "")) {
                        result[i] = "/";
                        receivedWordLetters[i] = "";
                        localWordLetters[i] = "";
                        break;
                    }
                }
            }
            else {
                for (int j = 0; j < localWordLetter.length(); j++) {
                    if (receivedWordLetter.contains(localWordLetter.charAt(j) + "")) {
                        result[i] = "/";
                        receivedWordLetters[i] = "";
                        localWordLetters[i] = "";
                        break;
                    }
                }
            }

            // get the longest string
            String longestString = receivedWordLetter.length() > localWordLetter.length() ? receivedWordLetter : localWordLetter;
        }

        // check blue color
        for (int i = 0; i < receivedWordLetters.length; i++) {
            if (!receivedWordLetters[i].isEmpty()) {
                // check if not contains
                if (!frenchWord.contains(receivedWordLetters[i])) {
                    result[i] = "-";
                    receivedWordLetters[i] = "";
                    localWordLetters[i] = "";
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

    @GetMapping("/playZone")
    public String playZone(Model model, Locale locale) {
        String pageTitle = messageSource.getMessage("PlayZone.PageTitle", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        return "playZone";
    }

    @GetMapping("/getYamlData")
    @ResponseBody
    public String getYamlData() throws IOException {
        Resource file = resourceLoader.getResource("classpath:static/assets/elements.yml");
        return file.getContentAsString(StandardCharsets.UTF_8);
    }

    @PostMapping("/sendWord")
    @ResponseBody
    public String receiveWord(@RequestBody String word) {
        System.out.println("Received word: " + word);
        // Perform any necessary operations with the received word

        return compareWords(word);
    }
}