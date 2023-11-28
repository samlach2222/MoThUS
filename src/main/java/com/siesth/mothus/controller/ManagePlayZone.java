package com.siesth.mothus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Controller
public class ManagePlayZone {
    private final MessageSource messageSource;
    private final ResourceLoader resourceLoader;

    @Autowired
    public ManagePlayZone(MessageSource messageSource, ResourceLoader resourceLoader) {
        this.messageSource = messageSource;
        this.resourceLoader = resourceLoader;
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

    // TODO : Function to pass sub-dictionary to frontend (a dictionary of words starting with a specific letter)

    @GetMapping("/getTodayWordData")
    @ResponseBody
    public String getTodayWordData() throws IOException {
        String word = "SAlUTaTiONS"; // case is very important --> THIS IS TEMPORARY, GET FROM DB
        // split each upper case letter
        String[] letters = word.split("(?=[A-Z])");
        int length = letters.length;
        String firstLetter = letters[0];

        // return length and first letter
        return length + " " + firstLetter;
    }
}