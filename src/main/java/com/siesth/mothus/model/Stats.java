/**
 * @file Stats.java
 * @brief Class to have the stats of the user
 * @date 2023-11-25
 * @version 1.0
 */

package com.siesth.mothus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Class to have the stats of the user
 */
@Entity
public class Stats {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int idStats;

    /**
     * Number of times the user found the word at the first try
     */
    int firstTryCount;

    /**
     * Number of times the user found the word at the second try
     */
    int secondTryCount;

    /**
     * Number of times the user found the word at the third try
     */
    int thirdTryCount;

    /**
     * Number of times the user found the word at the fourth try
     */
    int fourthTryCount;

    /**
     * Number of times the user found the word at the fifth try
     */
    int fifthTryCount;

    /**
     * Number of times the user found the word at the sixth try
     */
    int sixthTryCount;

    /**
     * Number of times the user found the word at the seventh try
     */
    int seventhTryCount;

    /**
     * Number of times the user found the word at the eighth try
     */
    int eighthTryCount;

    /**
     * Number of times the user won
     */
    int winCount;

    /**
     * Number of times the user lost
     */
    int looseCount;

    /**
     * Time played by the user in seconds
     */
    int playTime;

    /**
     * Number of times the user used the red square
     */
    int redSquareCount;

    /**
     * Number of times the user used the blue square
     */
    int blueSquareCount;

    /**
     * Number of times the user used the purple square
     */
    int purpleSquareCount;

    /**
     * Number of times the user used the yellow circle
     */
    int yellowCircleCount;

    /**
     * Constructor of the stats
     */
    public Stats(){
        firstTryCount = 0;
        secondTryCount = 0;
        thirdTryCount = 0;
        fourthTryCount = 0;
        fifthTryCount = 0;
        sixthTryCount = 0;
        seventhTryCount = 0;
        eighthTryCount = 0;
        winCount = 0;
        looseCount = 0;
        playTime = 0;
        redSquareCount = 0;
        blueSquareCount = 0;
        yellowCircleCount = 0;
    }

    /**
     * Getter of the first try count
     * @return number of times the user found the word at the first try
     */
    public int getFirstTryCount() {
        return firstTryCount;
    }

    /**
     * Getter of the second try count
     * @return number of times the user found the word at the second try
     */
    public int getSecondTryCount() {
        return secondTryCount;
    }

    /**
     * Getter of the third try count
     * @return number of times the user found the word at the third try
     */
    public int getThirdTryCount() {
        return thirdTryCount;
    }

    /**
     * Getter of the fourth try count
     * @return number of times the user found the word at the fourth try
     */
    public int getFourthTryCount() {
        return fourthTryCount;
    }

    /**
     * Getter of the fifth try count
     * @return number of times the user found the word at the fifth try
     */
    public int getFifthTryCount() {
        return fifthTryCount;
    }

    /**
     * Getter of the sixth try count
     * @return number of times the user found the word at the sixth try
     */
    public int getSixthTryCount() {
        return sixthTryCount;
    }

    /**
     * Getter of the seventh try count
     * @return number of times the user found the word at the seventh try
     */
    public int getSeventhTryCount() {
        return seventhTryCount;
    }

    /**
     * Getter of the eighth try count
     * @return number of times the user found the word at the eighth try
     */
    public int getEighthTryCount() {
        return eighthTryCount;
    }

    /**
     * Getter of the win count
     * @return number of times the user won
     */
    public int getWinCount() {
        return winCount;
    }

    /**
     * Getter of the loose count
     * @return number of times the user lost
     */
    public int getLooseCount() {
        return looseCount;
    }

    /**
     * Getter of the play time
     * @return time played by the user in seconds
     */
    public int getPlayTime() {
        return playTime;
    }

    /**
     * Getter of the red square count
     * @return number of times the user used the red square
     */
    public int getRedSquareCount() {
        return redSquareCount;
    }

    /**
     * Getter of the blue square count
     * @return number of times the user used the blue square
     */
    public int getBlueSquareCount() {
        return blueSquareCount;
    }

    /**
     * Getter of the purple square count
     * @return number of times the user used the purple square
     */
    public int getPurpleSquareCount() {
        return purpleSquareCount;
    }

    /**
     * Getter of the yellow circle count
     * @return number of times the user used the yellow circle
     */
    public int getYellowCircleCount() {
        return yellowCircleCount;
    }

    /**
     * Setter of the first try count
     * @param firstTryCount number of times the user found the word at the first try
     */
    public void setFirstTryCount(int firstTryCount) {
        this.firstTryCount = firstTryCount;
    }

    /**
     * Setter of the second try count
     * @param secondTryCount number of times the user found the word at the second try
     */
    public void setSecondTryCount(int secondTryCount) {
        this.secondTryCount = secondTryCount;
    }

    /**
     * Setter of the third try count
     * @param thirdTryCount number of times the user found the word at the third try
     */
    public void setThirdTryCount(int thirdTryCount) {
        this.thirdTryCount = thirdTryCount;
    }

    /**
     * Setter of the fourth try count
     * @param fourthTryCount number of times the user found the word at the fourth try
     */
    public void setFourthTryCount(int fourthTryCount) {
        this.fourthTryCount = fourthTryCount;
    }

    /**
     * Setter of the fifth try count
     * @param fifthTryCount number of times the user found the word at the fifth try
     */
    public void setFifthTryCount(int fifthTryCount) {
        this.fifthTryCount = fifthTryCount;
    }

    /**
     * Setter of the sixth try count
     * @param sixthTryCount number of times the user found the word at the sixth try
     */
    public void setSixthTryCount(int sixthTryCount) {
        this.sixthTryCount = sixthTryCount;
    }

    /**
     * Setter of the seventh try count
     * @param seventhTryCount number of times the user found the word at the seventh try
     */
    public void setSeventhTryCount(int seventhTryCount) {
        this.seventhTryCount = seventhTryCount;
    }

    /**
     * Setter of the eighth try count
     * @param eighthTryCount number of times the user found the word at the eighth try
     */
    public void setEighthTryCount(int eighthTryCount) {
        this.eighthTryCount = eighthTryCount;
    }

    /**
     * Setter of the win count
     * @param winCount number of times the user won
     */
    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    /**
     * Setter of the loose count
     * @param looseCount number of times the user lost
     */
    public void setLooseCount(int looseCount) {
        this.looseCount = looseCount;
    }

    /**
     * Setter of the play time
     * @param playTime time played by the user in seconds
     */
    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    /**
     * Setter of the red square count
     * @param redSquareCount number of times the user used the red square
     */
    public void setRedSquareCount(int redSquareCount) {
        this.redSquareCount = redSquareCount;
    }

    /**
     * Setter of the blue square count
     * @param blueSquareCount number of times the user used the blue square
     */
    public void setBlueSquareCount(int blueSquareCount) {
        this.blueSquareCount = blueSquareCount;
    }

    /**
     * Setter of the purple square count
     * @param purpleSquareCount number of times the user used the purple square
     */
    public void setPurpleSquareCount(int purpleSquareCount) {
        this.purpleSquareCount = purpleSquareCount;
    }

    /**
     * Setter of the yellow circle count
     * @param yellowCircleCount number of times the user used the yellow circle
     */
    public void setYellowCircleCount(int yellowCircleCount) {
        this.yellowCircleCount = yellowCircleCount;
    }

}