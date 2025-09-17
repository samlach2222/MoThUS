/**
 * @file User.java
 * @brief Class of a user
 * @date 2023-11-25
 * @version 1.0
 */

package com.siesth.mothus.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * Class of a user
 */
@Entity
public class User {
    /**
     * ID of the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int idUser;

    /**
     * Username of the user
     */
    String username;

    /**
     * Mail of the user
     */
    String mail;

    /**
     * Salted hashed password of the user
     */
    String password;

    /**
     * Language of the user
     */
    UserLanguage currentLanguage;

    /**
     * Balance of in-game currency of the user
     */
    int balance;

    /**
     * Stats of the user
     */
    @OneToOne(cascade = CascadeType.ALL)
    Stats stats;

    /**
     * Skin inventory of the user
     */
    @OneToOne(cascade = CascadeType.ALL)
    SkinInventory skinInventory;

    /**
     * Validation code of the user
     */
    @OneToOne  // Causes problems when removing it if cascade set to CascadeType.ALL
            ValidationCode validationCode;

    /**
     * Empty constructor
     */
    public User() {

    }

    /**
     * Constructor of a user
     * @param username        username of the user
     * @param mail            mail of the user
     * @param password        raw password of the user
     * @param currentLanguage language of the user
     * @param balance         in-game currency balance of the user
     * @param stats           stats of the user
     * @param skinInventory   skin inventory of the user
     */
    public User(String username, String mail, String password, UserLanguage currentLanguage, int balance, Stats stats, SkinInventory skinInventory) {
        setUsername(username);
        setMail(mail);
        setPassword(password);
        setCurrentLanguage(currentLanguage);
        setBalance(balance);
        setStats(stats);
        setSkinInventory(skinInventory);
    }

    /**
     * Getter of the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of the username
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of the mail
     * @return mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Setter of the mail
     * @param mail mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Getter of the salted hashed password
     * @return salted hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Salt hash the given password and set it
     * @param password raw password
     */
    public void setPassword(String password) {
        // Create salt
        Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 50000, 3);
        this.password = arg2SpringSecurity.encode(password);
    }

    /**
     * Getter of the language
     * @return language
     */
    public UserLanguage getCurrentLanguage() {
        return currentLanguage;
    }

    /**
     * Setter of the language
     * @param currentLanguage language
     */
    public void setCurrentLanguage(UserLanguage currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    /**
     * Getter of the in-game currency balance
     * @return in-game currency balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Setter of the in-game currency balance
     * @param balance in-game currency balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Getter of the stats
     * @return stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Setter of the stats
     * @param stats stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * Getter of the skin inventory
     * @return skin inventory
     */
    public SkinInventory getSkinInventory() {
        return skinInventory;
    }

    /**
     * Setter of the skin inventory
     * @param skinInventory skin inventory
     */
    public void setSkinInventory(SkinInventory skinInventory) {
        this.skinInventory = skinInventory;
    }

    /**
     * Getter of the validation code
     * @return validation code
     */
    public ValidationCode getValidationCode() {
        return validationCode;
    }

    /**
     * Setter of the validation code
     * @param validationCode validation code
     */
    public void setValidationCode(ValidationCode validationCode) {
        this.validationCode = validationCode;
    }
}
