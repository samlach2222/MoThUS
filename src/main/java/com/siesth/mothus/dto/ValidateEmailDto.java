package com.siesth.mothus.dto;

/**
 * Class to store data from email validation form
 */
public class ValidateEmailDto {
    /**
     * Code from email
     */
    private String code;
    /**
     * Username of the user
     */
    private String username;

    /**
     * Empty constructor
     */
    public ValidateEmailDto() {}

    /**
     * Getter for code
     * @return Code from email
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter for code
     * @param code Code from email
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter for username
     * @return Username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username
     * @param username Username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
