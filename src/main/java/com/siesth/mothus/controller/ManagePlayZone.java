package com.siesth.mothus.controller;

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

    private final String word;

    @Autowired
    public ManagePlayZone(MessageSource messageSource, ResourceLoader resourceLoader) {
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
        this.word = "SAlUTaTiONS"; // TODO : THIS IS TEMPORARY, GET FROM DB
    }

    private String compareWords(String receivedWord) {
        // remove empty strings and quote from receivedWord
        receivedWord = receivedWord.replace("\"", "");
        receivedWord = receivedWord.replace(" ", "");
        String[] localWordLetters = word.split("(?=[A-Z])");
        String[] receivedWordLetters = receivedWord.split("(?=[A-Z])");

        //TODO : Check yellow color because the rule is not completely implemented here
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < receivedWordLetters.length; i++) {
            if(receivedWordLetters[i].equals(localWordLetters[i])) {
                result.append("+");
            }
            else if(word.contains(receivedWordLetters[i])) {
                result.append("*");
            }
            else {
                result.append("-");
            }
        }
        return result.toString();
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

    @GetMapping("/getTodayWordData")
    @ResponseBody
    public String getTodayWordData() throws IOException {
        // split each upper case letter
        String[] letters = word.split("(?=[A-Z])");
        int length = letters.length;
        String firstLetter = letters[0];

        // return length and first letter
        return length + " " + firstLetter;
    }

    @PostMapping("/sendWord")
    @ResponseBody
    public String receiveWord(@RequestBody String word) {
        System.out.println("Received word: " + word);
        // Perform any necessary operations with the received word

        return compareWords(word);
    }
}