/**
 * @file Stats.java
 * @brief Class to have the stats of the user
 * @date 2023-11-25
 * @version 1.0
 * @author Titouan DEGIEUX
 * @author Samuel LACHAUD
 */

package com.siesth.mothus.model;

import jakarta.persistence.*;

/**
 * Class to have the stats of the user
 */
@Entity
public class Stats {

    /**
     * ID of the stats
     */
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
     * Time played by the user
     */
    int playTime; // in seconds

    /**
     * Number of red squares found by the user
     */
    int redSquareCount;

    /**
     * Number of blue squares found by the user
     */
    int blueSquareCount;

    /**
     * Number of yellow circles found by the user
     */
    int yellowCircleCount;

    /**
     * Empty constructor of the stats
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
     * Getter of the stats ID
     * @return stats ID
     */
    public int getIdStats() {
        return idStats;
    }

    /**
     * Getter of the first try count
     * @return first try count
     */
    public int getFirstTryCount() {
        return firstTryCount;
    }

    /**
     * Getter of the second try count
     * @return second try count
     */
    public int getSecondTryCount() {
        return secondTryCount;
    }

    /**
     * Getter of the third try count
     * @return third try count
     */
    public int getThirdTryCount() {
        return thirdTryCount;
    }

    /**
     * Getter of the fourth try count
     * @return fourth try count
     */
    public int getFourthTryCount() {
        return fourthTryCount;
    }

    /**
     * Getter of the fifth try count
     * @return fifth try count
     */
    public int getFifthTryCount() {
        return fifthTryCount;
    }

    /**
     * Getter of the sixth try count
     * @return sixth try count
     */
    public int getSixthTryCount() {
        return sixthTryCount;
    }

    /**
     * Getter of the seventh try count
     * @return seventh try count
     */
    public int getSeventhTryCount() {
        return seventhTryCount;
    }

    /**
     * Getter of the eighth try count
     * @return eighth try count
     */
    public int getEighthTryCount() {
        return eighthTryCount;
    }

    /**
     * Getter of the win count
     * @return win count
     */
    public int getWinCount() {
        return winCount;
    }

    /**
     * Getter of the loose count
     * @return loose count
     */
    public int getLooseCount() {
        return looseCount;
    }

    /**
     * Getter of the play time
     * @return play time
     */
    public int getPlayTime() {
        return playTime;
    }

    /**
     * Getter of the red square count
     * @return red square count
     */
    public int getRedSquareCount() {
        return redSquareCount;
    }

    /**
     * Getter of the blue square count
     * @return blue square count
     */
    public int getBlueSquareCount() {
        return blueSquareCount;
    }

    /**
     * Getter of the yellow circle count
     * @return yellow circle count
     */
    public int getYellowCircleCount() {
        return yellowCircleCount;
    }

    /**
     * Setter of the first try count
     * @param firstTryCount first try count
     */
    public void setFirstTryCount(int firstTryCount) {
        this.firstTryCount = firstTryCount;
    }

    /**
     * Setter of the second try count
     * @param secondTryCount second try count
     */
    public void setSecondTryCount(int secondTryCount) {
        this.secondTryCount = secondTryCount;
    }

    /**
     * Setter of the third try count
     * @param thirdTryCount third try count
     */
    public void setThirdTryCount(int thirdTryCount) {
        this.thirdTryCount = thirdTryCount;
    }

    /**
     * Setter of the fourth try count
     * @param fourthTryCount fourth try count
     */
    public void setFourthTryCount(int fourthTryCount) {
        this.fourthTryCount = fourthTryCount;
    }

    /**
     * Setter of the fifth try count
     * @param fifthTryCount fifth try count
     */
    public void setFifthTryCount(int fifthTryCount) {
        this.fifthTryCount = fifthTryCount;
    }

    /**
     * Setter of the sixth try count
     * @param sixthTryCount sixth try count
     */
    public void setSixthTryCount(int sixthTryCount) {
        this.sixthTryCount = sixthTryCount;
    }

    /**
     * Setter of the seventh try count
     * @param seventhTryCount seventh try count
     */
    public void setSeventhTryCount(int seventhTryCount) {
        this.seventhTryCount = seventhTryCount;
    }

    /**
     * Setter of the eighth try count
     * @param eighthTryCount eighth try count
     */
    public void setEighthTryCount(int eighthTryCount) {
        this.eighthTryCount = eighthTryCount;
    }

    /**
     * Setter of the win count
     * @param winCount win count
     */
    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    /**
     * Setter of the loose count
     * @param looseCount loose count
     */
    public void setLooseCount(int looseCount) {
        this.looseCount = looseCount;
    }

    /**
     * Setter of the play time
     * @param playTime play time
     */
    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    /**
     * Setter of the red square count
     * @param redSquareCount red square count
     */
    public void setRedSquareCount(int redSquareCount) {
        this.redSquareCount = redSquareCount;
    }

    /**
     * Setter of the blue square count
     * @param blueSquareCount blue square count
     */
    public void setBlueSquareCount(int blueSquareCount) {
        this.blueSquareCount = blueSquareCount;
    }

    /**
     * Setter of the yellow circle count
     * @param yellowCircleCount yellow circle count
     */
    public void setYellowCircleCount(int yellowCircleCount) {
        this.yellowCircleCount = yellowCircleCount;
    }

}
