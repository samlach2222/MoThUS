package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.model.Game;

public interface IGameManagement {

    /**
     * Returns the game for today by creating it if it doesn't exist
     * @return game of today
     */
    public Game getTodayGame();
}
