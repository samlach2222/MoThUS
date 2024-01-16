package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

/**
 * This class is used to manage the stats popup.
 */
@Controller
public class ManageStatsPopup {
    /**
     * This field is used to get the message from the message source.
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
    public ManageStatsPopup(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * This method is used to show the stats popup.
     * It adds the texts to the model from locale.
     * @param model the model
     * @param locale the locale
     * @return the stats popup page
     */
    @GetMapping("/statsPopup")
    public String statsPopup(Authentication authentication, Model model, Locale locale) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            String userLanguage = userManagement.getLanguageByUsername(currentUserName);
            locale = new Locale(userLanguage);
        }

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

        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Stats stats = userManagement.getStatsByUsername(currentUserName);
            model.addAttribute("victories", stats.getWinCount());
            model.addAttribute("looses", stats.getLooseCount());
            model.addAttribute("firstTry", stats.getFirstTryCount());
            model.addAttribute("secondTry", stats.getSecondTryCount());
            model.addAttribute("thirdTry", stats.getThirdTryCount());
            model.addAttribute("fourthTry", stats.getFourthTryCount());
            model.addAttribute("fifthTry", stats.getFifthTryCount());
            model.addAttribute("sixthTry", stats.getSixthTryCount());
            model.addAttribute("seventhTry", stats.getSeventhTryCount());
            model.addAttribute("eighthTry", stats.getEighthTryCount());
            model.addAttribute("looseTry", stats.getLooseCount());
            model.addAttribute("redSquareCount", stats.getRedSquareCount());
            model.addAttribute("blueSquareCount", stats.getBlueSquareCount());
            model.addAttribute("yellowCircleCount", stats.getYellowCircleCount());
            model.addAttribute("purpleSquareCount", stats.getPurpleSquareCount());
            int seconds = stats.getPlayTime();
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;
            seconds = seconds % 60;
            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            model.addAttribute("playtime", timeString);
        }

        return "Popup/statsPopup";
    }
}