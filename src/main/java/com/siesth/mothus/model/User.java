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
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    int idUser;

    String username;
    String mail;

    /**
     * Salted hashed password of the user
     */
    String password;

    UserLanguage currentLanguage;

    /**
     * Balance of in-game currency of the user
     */
    int balance;

    @OneToOne(cascade = CascadeType.ALL)
    Stats stats;

    @OneToOne(cascade = CascadeType.ALL)
    SkinInventory skinInventory;

    @OneToOne(cascade = CascadeType.ALL) // by default, it is optional = true
    ValidationCode validationCode;

    public User() {

    }

    /**
     * Constructor of a user
     * @param username username of the user
     * @param mail mail of the user
     * @param password raw password of the user
     * @param currentLanguage language of the user
     * @param balance in-game currency balance of the user
     * @param stats stats of the user
     * @param skinInventory skin inventory of the user
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

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
        Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
        this.password = arg2SpringSecurity.encode(password);
    }

    public UserLanguage getCurrentLanguage() {
        return currentLanguage;
    }

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

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public SkinInventory getSkinInventory() {
        return skinInventory;
    }

    public void setSkinInventory(SkinInventory skinInventory) {
        this.skinInventory = skinInventory;
    }

    public ValidationCode getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(ValidationCode validationCode) {
        this.validationCode = validationCode;
    }
}
