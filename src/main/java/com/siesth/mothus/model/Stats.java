package com.siesth.mothus.model;

import jakarta.persistence.*;

@Entity
public class Stats {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int statsId;

    @OneToOne(mappedBy = "user")
    User user;

    int firstTryCount;
    int secondTryCount;
    int thirdTryCount;
    int fourthTryCount;
    int fifthTryCount;
    int sixthTryCount;
    int seventhTryCount;
    int eighthTryCount;
    int winCount;
    int looseCount;
    int playTime; // in seconds
    int redSquareCount;
    int blueSquareCount;
    int yellowCircleCount;

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

    public int getStatsId() {
        return statsId;
    }

    public int getFirstTryCount() {
        return firstTryCount;
    }

    public int getSecondTryCount() {
        return secondTryCount;
    }

    public int getThirdTryCount() {
        return thirdTryCount;
    }

    public int getFourthTryCount() {
        return fourthTryCount;
    }

    public int getFifthTryCount() {
        return fifthTryCount;
    }

    public int getSixthTryCount() {
        return sixthTryCount;
    }

    public int getSeventhTryCount() {
        return seventhTryCount;
    }

    public int getEighthTryCount() {
        return eighthTryCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getLooseCount() {
        return looseCount;
    }

    public int getPlayTime() {
        return playTime;
    }

    public int getRedSquareCount() {
        return redSquareCount;
    }

    public int getBlueSquareCount() {
        return blueSquareCount;
    }

    public int getYellowCircleCount() {
        return yellowCircleCount;
    }

    public User getUser() {
        return user;
    }

    public void setFirstTryCount(int firstTryCount) {
        this.firstTryCount = firstTryCount;
    }

    public void setSecondTryCount(int secondTryCount) {
        this.secondTryCount = secondTryCount;
    }

    public void setThirdTryCount(int thirdTryCount) {
        this.thirdTryCount = thirdTryCount;
    }

    public void setFourthTryCount(int fourthTryCount) {
        this.fourthTryCount = fourthTryCount;
    }

    public void setFifthTryCount(int fifthTryCount) {
        this.fifthTryCount = fifthTryCount;
    }

    public void setSixthTryCount(int sixthTryCount) {
        this.sixthTryCount = sixthTryCount;
    }

    public void setSeventhTryCount(int seventhTryCount) {
        this.seventhTryCount = seventhTryCount;
    }

    public void setEighthTryCount(int eighthTryCount) {
        this.eighthTryCount = eighthTryCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public void setLooseCount(int looseCount) {
        this.looseCount = looseCount;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public void setRedSquareCount(int redSquareCount) {
        this.redSquareCount = redSquareCount;
    }

    public void setBlueSquareCount(int blueSquareCount) {
        this.blueSquareCount = blueSquareCount;
    }

    public void setYellowCircleCount(int yellowCircleCount) {
        this.yellowCircleCount = yellowCircleCount;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
