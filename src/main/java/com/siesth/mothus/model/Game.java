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

    /**
     * Id of the game
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int idGame;

    /**
     * Date of the game
     */
    @Column(columnDefinition = "datetime") // The default "datetime(6)" is not supported in our server
    LocalDate dateOfTheGame;

    /**
     * French word
     */
    String frenchWord;

    /**
     * English word
     */
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

    /**
     * Empty constructor
     */
    public Game() {

    }

    /**
     * Getter of the date of the game
     * @return date of the game
     */
    public LocalDate getDateOfTheGame() {
        return dateOfTheGame;
    }

    /**
     * Setter of the date of the game
     * @param dateOfTheGame date of the game
     */
    public void setDateOfTheGame(LocalDate dateOfTheGame) {
        this.dateOfTheGame = dateOfTheGame;
    }

    /**
     * Getter of the French word
     * @return French word
     */
    public String getFrenchWord() {
        return frenchWord;
    }

    /**
     * Setter of the French word
     * @param frenchWord French word
     */
    public void setFrenchWord(String frenchWord) {
        this.frenchWord = frenchWord;
    }

    /**
     * Getter of the English word
     * @return English word
     */
    public String getEnglishWord() {
        return englishWord;
    }

    /**
     * Setter of the English word
     * @param englishWord English word
     */
    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public int getIdGame() {
        return idGame;
    }
}
