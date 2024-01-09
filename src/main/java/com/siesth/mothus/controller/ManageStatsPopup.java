package com.siesth.mothus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class ManageStatsPopup {
    private final MessageSource messageSource;

    @Autowired
    public ManageStatsPopup(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/statsPopup")
    public String statsPopup(Model model, Locale locale) {
        String pageTitle = messageSource.getMessage("StatsPopup.PageTitle", null, locale);
        String elementsHandledLabel = messageSource.getMessage("StatsPopup.ElementsHandledLabel", null, locale);
        String gameNotFinished = messageSource.getMessage("StatsPopup.GameNotFinished", null, locale);
        String globalTitle = messageSource.getMessage("StatsPopup.GlobalTitle", null, locale);
        String loosesLabel = messageSource.getMessage("StatsPopup.LoosesLabel", null, locale);
        String playTimeLabel = messageSource.getMessage("StatsPopup.PlayTimeLabel", null, locale);
        String statisticsTitle = messageSource.getMessage("StatsPopup.StatisticsTitle", null, locale);
        String victoriesLabel = messageSource.getMessage("StatsPopup.VictoriesLabel", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("elementsHandledLabel", elementsHandledLabel);
        model.addAttribute("gameNotFinished", gameNotFinished);
        model.addAttribute("globalTitle", globalTitle);
        model.addAttribute("loosesLabel", loosesLabel);
        model.addAttribute("playTimeLabel", playTimeLabel);
        model.addAttribute("statisticsTitle", statisticsTitle);
        model.addAttribute("victoriesLabel", victoriesLabel);

        return "Popup/statsPopup";
    }
}