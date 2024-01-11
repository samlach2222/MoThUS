/**
 * @file Skin.java
 * @brief Class to create a skin
 * @date 2023-11-24
 * @version 1.0
 */
package com.siesth.mothus.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Random;

@Entity
public class ValidationCode {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int idValidationCode;

    @Column(columnDefinition = "datetime") // The default "datetime(6)" is not supported in our server
    LocalDateTime creationDate;

    /**
     * Duration of the validation code in seconds
     */
    int duration;

    String code;


    public ValidationCode(LocalDateTime creationDate, int duration, String code){
        super();
        this.setCreationDate(creationDate);
        this.setDuration(duration);
        this.setCode(code);
    }

    public ValidationCode() {

        // get current server date
        this.setCreationDate(LocalDateTime.now());
        this.setDuration(900);

        // code is a string of 6 random digits
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        this.setCode(sb.toString());
    }

    public int getIdValidationCode() {
        return idValidationCode;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public int getDuration() {
        return duration;
    }

    public String getCode() {
        return code;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
