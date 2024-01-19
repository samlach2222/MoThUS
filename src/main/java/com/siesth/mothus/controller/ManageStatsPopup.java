package com.siesth.mothus.controller;

import com.siesth.mothus.dataManagementService.IGameManagement;
import com.siesth.mothus.dataManagementService.IUserManagement;
import com.siesth.mothus.model.Stats;
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

    @Autowired
    private IGameManagement gameManagement;

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
     * @param model  the model
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
        String shareButton = messageSource.getMessage("StatsPopup.ShareButton", null, locale);

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("elementsHandledLabel", elementsHandledLabel);
        model.addAttribute("gameNotFinished", gameNotFinished);
        model.addAttribute("globalTitle", globalTitle);
        model.addAttribute("loosesLabel", loosesLabel);
        model.addAttribute("playTimeLabel", playTimeLabel);
        model.addAttribute("statisticsTitle", statisticsTitle);
        model.addAttribute("victoriesLabel", victoriesLabel);
        model.addAttribute("shareButton", shareButton);

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
            int secondsModulo60 = seconds % 60;
            int minutes = seconds / 60;
            int hours = seconds / 60 / 60;

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, secondsModulo60);
            model.addAttribute("playtime", timeString);
        }

        return "Popup/statsPopup";
    }

    @GetMapping("/getLatestGameNumber")
    @ResponseBody
    public int getLatestGameNumber() {
        return gameManagement.getTodayGame().getIdGame();
    }

    @PostMapping("/exportResultToServer")
    @ResponseBody
    public Integer exportResultToServer(Authentication authentication, @RequestBody Map<String, String> resultInfo) {
        int gameNumber = Integer.parseInt(resultInfo.get("gameNumber"));
        int time = Integer.parseInt(resultInfo.get("time"));
        int numberOfTries = Integer.parseInt(resultInfo.get("numberOfTries"));
        int numberOfRedSquare = Integer.parseInt(resultInfo.get("numberOfRedSquare"));
        int numberOfBlueSquare = Integer.parseInt(resultInfo.get("numberOfBlueSquare"));
        int numberOfYellowCircle = Integer.parseInt(resultInfo.get("numberOfYellowCircle"));
        int numberOfPurpleSquare = Integer.parseInt(resultInfo.get("numberOfPurpleSquare"));
        boolean win = Boolean.parseBoolean(resultInfo.get("win"));

        // get current user
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            Stats stats = userManagement.getStatsByUsername(currentUserName);
            stats.setPlayTime(stats.getPlayTime() + time);
            stats.setBlueSquareCount(stats.getBlueSquareCount() + numberOfBlueSquare);
            stats.setRedSquareCount(stats.getRedSquareCount() + numberOfRedSquare);
            stats.setYellowCircleCount(stats.getYellowCircleCount() + numberOfYellowCircle);
            stats.setPurpleSquareCount(stats.getPurpleSquareCount() + numberOfPurpleSquare);
            stats.setWinCount(stats.getWinCount() + (win ? 1 : 0));
            int amountOfMollardsWin = 0;
            if (win) {
                switch (numberOfTries) {
                    case 1:
                        stats.setFirstTryCount(stats.getFirstTryCount() + 1);
                        amountOfMollardsWin = 100;
                        break;
                    case 2:
                        stats.setSecondTryCount(stats.getSecondTryCount() + 1);
                        amountOfMollardsWin = 30;
                        break;
                    case 3:
                        stats.setThirdTryCount(stats.getThirdTryCount() + 1);
                        amountOfMollardsWin = 20;
                        break;
                    case 4:
                        stats.setFourthTryCount(stats.getFourthTryCount() + 1);
                        amountOfMollardsWin = 15;
                        break;
                    case 5:
                        stats.setFifthTryCount(stats.getFifthTryCount() + 1);
                        amountOfMollardsWin = 12;
                        break;
                    case 6:
                        stats.setSixthTryCount(stats.getSixthTryCount() + 1);
                        amountOfMollardsWin = 10;
                        break;
                    case 7:
                        stats.setSeventhTryCount(stats.getSeventhTryCount() + 1);
                        amountOfMollardsWin = 7;
                        break;
                    case 8:
                        stats.setEighthTryCount(stats.getEighthTryCount() + 1);
                        amountOfMollardsWin = 4;
                        break;
                }
            } else {
                stats.setLooseCount(stats.getLooseCount() + 1);
                amountOfMollardsWin = -1;
            }
            userManagement.updateStatsByUsername(currentUserName, stats);
            userManagement.updateBalanceByUsername(currentUserName, amountOfMollardsWin);
            return amountOfMollardsWin;
        } else {
            return null;
        }
    }
}