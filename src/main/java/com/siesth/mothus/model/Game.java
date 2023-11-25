/**
 * @file Game.java
 * @brief Class to have the daily game
 * @date 2023-11-24
 * @version 1.0
 */

package com.siesth.mothus.model;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Class to have the daily game
 */
@Entity
public class Game {

    /**
     * ID of the game
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int idGame;

    /**
     * Date of the game
     */
    @Column(columnDefinition = "datetime") // The default "datetime(6)" is not supported in our server
    Date dateOfTheGame;

    /**
     * French word of the game
     */
    String frenchWord;

    /**
     * English word of the game
     */
    String englishWord;

    /**
     * Constructor of the game
     * @param dateOfTheGame date of the game
     * @param frenchWord French word
     * @param englishWord English word
     */
    public Game(Date dateOfTheGame, String frenchWord, String englishWord) {
        super();
        this.setDateOfTheGame(dateOfTheGame);
        this.setFrenchWord(frenchWord);
        this.setEnglishWord(englishWord);
    }

    /**
     * Empty constructor of the game
     */
    public Game() {

    }

    /**
     * Getter of the game ID
     * @return ID of the game
     */
    public int getIdGame() {
        return idGame;
    }

    /**
     * Getter of the game date
     * @return date of the game
     */
    public Date getDateOfTheGame() {
        return dateOfTheGame;
    }

    /**
     * Setter of the game date
     * @param dateOfTheGame date of the game
     */
    public void setDateOfTheGame(Date dateOfTheGame) {
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
}
