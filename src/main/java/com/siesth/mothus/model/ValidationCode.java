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

/**
 * Class to create a validation code
 */
@Entity
public class ValidationCode {

    /**
     * Id of the validation code
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int idValidationCode;

    /**
     * Date and time of the creation of the validation code
     */
    @Column(columnDefinition = "datetime") // The default "datetime(6)" is not supported in our server
    LocalDateTime creationDate;

    /**
     * Duration of the validation code
     */
    int duration;

    /**
     * Code of the validation code
     */
    String code;

    /**
     * Constructor of the validation code
     * @param creationDate date and time of the creation of the validation code
     * @param duration duration of the validation code
     * @param code code of the validation code
     */
    public ValidationCode(LocalDateTime creationDate, int duration, String code){
        super();
        this.setCreationDate(creationDate);
        this.setDuration(duration);
        this.setCode(code);
    }

    /**
     * Empty constructor that generates a random code
     */
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

    /**
     * Getter of the id of the validation code
     * @return id of the validation code
     */
    public int getIdValidationCode() {
        return idValidationCode;
    }

    /**
     * Getter of the date and time of the creation of the validation code
     * @return date and time of the creation of the validation code
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Getter of the duration of the validation code
     * @return duration of the validation code
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Getter of the code of the validation code
     * @return code of the validation code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter of the creation date and time of the validation code
     * @param creationDate date and time of the creation of the validation code
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Setter of the duration of the validation code
     * @param duration duration of the validation code
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Setter of the code of the validation code
     * @param code code of the validation code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
