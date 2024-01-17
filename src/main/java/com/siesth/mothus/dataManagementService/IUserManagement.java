package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.model.Skin;
import com.siesth.mothus.model.SkinInventory;
import com.siesth.mothus.model.Stats;
import com.siesth.mothus.model.ValidationCode;

/**
 * This interface is used to manage a user.
 */
public interface IUserManagement {

    /**
     * This method is used to create a new user from registration data from form.
     * @param registrationDto the registration data from form
     * @return true if the user has been created, false otherwise (false is when the username or the email already exists)
     */
    boolean createNewUser(RegistrationDto registrationDto);

    /**
     * This method is used to check if the password is valid.
     * @param registrationDto the registration data from form
     * @return true if the password is valid, false otherwise
     */
    boolean checkLogin(RegistrationDto registrationDto);

    /**
     * This method is used to check if the username is taken.
     * @param username the username
     * @return true if the username is taken, false otherwise
     */
    boolean isUsernameTaken(String username);

    /**
     * This method is used to check if the mail is taken.
     * @param mail the mail
     * @return true if the mail is taken, false otherwise
     */
    boolean isMailTaken(String mail);

    /**
     * This method is used to get the user by username and update the validation code.
     * @param username the username
     * @param validationCode the validation code
     */
    void getUserByUsernameAndUpdateValidationCode(String username, ValidationCode validationCode);

    /**
     * This method is used to get the email by username.
     * @param username the username
     * @return the email
     */
    String getEmailByUsername(String username);

    /**
     * This method is used to get the language by username.
     * @param username the username
     * @return the language
     */
    String getLanguageByUsername(String username);

    /**
     * This method is used to get the stats by username.
     * @param username the username
     * @return stats
     */
    Stats getStatsByUsername(String username);

    /**
     * This method is used to get the skin inventory by username.
     * @param username the username
     * @return the skin inventory
     */
    SkinInventory getSkinInventoryByUsername(String username);

    /**
     * This method is used to get the password by username.
     * @param username the username
     * @return the password
     */
    String getPasswordByUsername(String username);

    /**
     * This method is used to get the balance by username.
     * @param username the username
     * @return the balance (Mollards)
     */
    int getMollardsByUsername(String username);

    /**
     * This method is used to update the language by username.
     * @param username the username
     * @param language the language
     */
    void updateLanguageByUsername(String username, String language);

    /**
     * This method is used to update the username by username.
     * @param oldUsername the old username
     * @param newUsername the new username
     */
    void updateUsernameByUsername(String oldUsername, String newUsername);

    void updateBalanceByUsername(String oldUsername, int balance);

    /**
     * This method is used to update the password by username.
     * @param username the username
     * @param password the password
     */
    void updatePasswordByUsername(String username, String password);

    /**
     * This method is used to update the mail by username.
     * @param username the username
     * @param email the email
     */
    void updateEmailByUsername(String username, String email);

    void updateStatsByUsername(String username, Stats stats);

    Skin getRandomSkin(String username, String type);

    void updateSkinInventoryByUsername(String username, SkinInventory skinInventory);

    void addBalanceByUsername(String username, int amount);
}
