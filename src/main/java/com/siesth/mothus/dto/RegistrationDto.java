package com.siesth.mothus.dto;

/**
 * Class to store data from registration form
 */
public class RegistrationDto {
    /**
     * Username of the user
     */
    private String username;

    /**
     * Email of the user
     */
    private String email;

    /**
     * Password of the user
     */
    private String password;

    /**
     * Password confirmation of the user
     */
    private String passwordConfirm;

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

    /**
     * Getter for email
     * @return Email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email
     * @param mail Email of the user
     */
    public void setEmail(String mail) {
        this.email = mail;
    }

    /**
     * Getter for password
     * @return Password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     * @param password Password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
