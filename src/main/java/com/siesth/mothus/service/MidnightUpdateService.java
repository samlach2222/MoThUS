package com.siesth.mothus.service;


import com.siesth.mothus.model.Game;
import com.siesth.mothus.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MidnightUpdateService {

    @Autowired
    private WordService wordService;

    @Autowired
    private GameRepository gameRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Run at midnight every day
    public void updateWordsAtMidnight() {
        // Fetch the latest game entity, assuming there's only one entry for each day
        Game latestGame = gameRepository.findTopByOrderByDateOfTheGameDesc();

        if (latestGame != null) {
            wordService.updateGameWordsAtMidnight(latestGame);
            System.out.println("Words updated at midnight for the game on " + latestGame.getDateOfTheGame());
        }
    }
}

