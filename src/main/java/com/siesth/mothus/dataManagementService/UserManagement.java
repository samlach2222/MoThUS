package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.model.*;
import com.siesth.mothus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class is used to manage the user management.
 */
@Service
public class UserManagement implements IUserManagement {
    /**
     * This field is used to get the user repository.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * This method is used to create a new user from registration data from form.
     * @param registrationDto the registration data from form
     * @return true if the user has been created, false otherwise (false is when the username or the email already exists)
     */
    @Override
    public boolean createNewUser(RegistrationDto registrationDto) {
        if (userRepository.existsUserByUsername(registrationDto.getUsername()) || userRepository.existsUserByMail(registrationDto.getEmail())) {
            return false;
        }
        else {
            User user = new User(
                    registrationDto.getUsername(),
                    registrationDto.getEmail(),
                    registrationDto.getPassword(),
                    UserLanguage.en,
                    0,
                    new Stats(),
                    new SkinInventory());
            userRepository.save(user);
            return true;
        }
    }

    /**
     * This method is used to check if the password is valid.
     * @param registrationDto the registration data from form
     * @return true if the password is valid, false otherwise
     */
    @Override
    public boolean checkLogin(RegistrationDto registrationDto) {
        User user = userRepository.findUserByUsername(registrationDto.getUsername());
        if (user == null) {
            return false;
        }
        Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
        return arg2SpringSecurity.matches(registrationDto.getPassword(), user.getPassword());
    }

    /**
     * This method is used to get the user by username and update the validation code.
     * @param username the username
     * @param validationCode the validation code
     */
    @Override
    public void getUserByUsernameAndUpdateValidationCode(String username, ValidationCode validationCode) {
        User user = userRepository.findUserByUsername(username);
        user.setValidationCode(validationCode);
        userRepository.save(user);
    }

    /**
     * This method is used to get the email by username.
     * @param username the username
     * @return the email
     */
    @Override
    public String getEmailByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getMail();
    }

    /**
     * This method is used to get the language by username.
     * @param username the username
     * @return the language
     */
    @Override
    public String getLanguageByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getCurrentLanguage().toString();
    }

    /**
     * This method is used to get the stats by username.
     * @param username the username
     * @return stats
     */
    @Override
    public Stats getStatsByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getStats();
    }

    /**
     * This method is used to get the skin inventory by username.
     * @param username the username
     * @return the skin inventory
     */
    @Override
    public SkinInventory getSkinInventoryByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getSkinInventory();
    }

    /**
     * This method is used to update the language by username.
     * @param username the username
     * @param language the language
     */
    @Override
    public void updateLanguageByUsername(String username, String language) {
        User user = userRepository.findUserByUsername(username);
        user.setCurrentLanguage(UserLanguage.valueOf(language));
        userRepository.save(user);
    }

    /**
     * This method is used to update the username by username.
     * @param oldUsername the old username
     * @param newUsername the new username
     */
    @Override
    public void updateUsernameByUsername(String oldUsername, String newUsername) {
        User user = userRepository.findUserByUsername(oldUsername);
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    /**
     * This method is used to update the password by username.
     * @param username the username
     * @param password the password
     */
    @Override
    public void updatePasswordByUsername(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }
}
