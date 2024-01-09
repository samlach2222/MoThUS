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
        this.setDuration(5);

        // get random string with 8 characters (a-z, A-Z, 0-9)
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while (sb.length() < 8) {
            int index = (int) (random.nextFloat() * chars.length());
            sb.append(chars.charAt(index));
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
