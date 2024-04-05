package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.model.Game;
import com.siesth.mothus.model.UserLanguage;

/**
 * This interface is used to manage the game.
 */
public interface IGameManagement {
    /**
     * Returns the game for today by creating it if it doesn't exist
     * @return game of today
     */
    Game getTodayGame();

    /**
     * Returns a random word from the given language using the full dictionary
     * @param userLanguage The language to get a word from
     * @return a random word from the given language
     */
    String getRandomWord(UserLanguage userLanguage);
}
