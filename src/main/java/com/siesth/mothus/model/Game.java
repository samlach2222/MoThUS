package com.siesth.mothus.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int idGame;

    Date dateOfTheGame;

    String frenchWord;

    String englishWord;

    public int getIdGame() {
        return idGame;
    }

    public Date getDateOfTheGame() {
        return dateOfTheGame;
    }

    public void setDateOfTheGame(Date dateOfTheGame) {
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
