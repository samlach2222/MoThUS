/**
 * @file Game.java
 * @brief Class to have the daily game
 * @date 2023-11-24
 * @version 1.0
 */

package com.siesth.mothus.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Class to have the daily game
 */
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int idGame;

    @Column(columnDefinition = "datetime") // The default "datetime(6)" is not supported in our server
    LocalDate dateOfTheGame;

    String frenchWord;

    String englishWord;

    /**
     * Constructor of the game
     * @param dateOfTheGame date of the game
     * @param frenchWord French word
     * @param englishWord English word
     */
    public Game(LocalDate dateOfTheGame, String frenchWord, String englishWord) {
        super();
        this.setDateOfTheGame(dateOfTheGame);
        this.setFrenchWord(frenchWord);
        this.setEnglishWord(englishWord);
    }

    public Game() {

    }

    public int getIdGame() {
        return idGame;
    }

    public LocalDate getDateOfTheGame() {
        return dateOfTheGame;
    }

    public void setDateOfTheGame(LocalDate dateOfTheGame) {
        this.dateOfTheGame = dateOfTheGame;
    }

    public String getFrenchWord() {
        return frenchWord;
    }

    public void setFrenchWord(String frenchWord) {
        this.frenchWord = frenchWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }
}
