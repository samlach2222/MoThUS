package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.model.*;
import com.siesth.mothus.repository.SkinRepository;
import com.siesth.mothus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Autowired
    SkinRepository skinRepository;

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
            Skin skinGrayElement = skinRepository.findSkinByIdSkin(1);
            Skin skinLightPage = skinRepository.findSkinByIdSkin(12);
            Skin skinDarkPage = skinRepository.findSkinByIdSkin(13);
            user.getSkinInventory().addSkinToList(skinGrayElement);
            user.getSkinInventory().addSkinToList(skinLightPage);
            user.getSkinInventory().addSkinToList(skinDarkPage);
            user.getSkinInventory().setCurrentElementSkinId(1);
            user.getSkinInventory().setCurrentPageSkinId(12);
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
     * This method is used to check if the username is taken.
     * @param username the username
     * @return true if the username is taken, false otherwise
     */
    @Override
    public boolean isUsernameTaken(String username) {
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

    /**
     * This method is used to check if the mail is taken.
     * @param mail the mail
     * @return true if the mail is taken, false otherwise
     */
    @Override
    public boolean isMailTaken(String mail) {
        User user = userRepository.findUserByMail(mail);
        return user != null;
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
     * This method is used to get the password by username.
     * @param username the username
     * @return the password
     */
    @Override
    public String getPasswordByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getPassword();
    }

    /**
     * This method is used to get the balance by username.
     * @param username the username
     * @return the balance (Mollards)
     */
    @Override
    public int getMollardsByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getBalance();
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

    @Override
    public void updateBalanceByUsername(String oldUsername, int balance) {
        User user = userRepository.findUserByUsername(oldUsername);
        user.setBalance(user.getBalance() + balance);
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

    /**
     * This method is used to update the mail by username.
     * @param username the username
     * @param email the email
     */
    @Override
    public void updateEmailByUsername(String username, String email) {
        User user = userRepository.findUserByUsername(username);
        user.setMail(email);
        userRepository.save(user);
    }

    @Override
    public void updateStatsByUsername(String username, Stats stats) {
        User user = userRepository.findUserByUsername(username);
        user.setStats(stats);
        userRepository.save(user);
    }

    @Override
    public Skin getRandomSkin(String username, String type) {
        List<Skin> skins = skinRepository.findAll();
        Collection<Skin> ownedSkins = getSkinInventoryByUsername(username).getSkinList();
        List<Skin> availableSkins = new ArrayList<>();
        for(Skin s : skins){
            if(s.getRarity().toString().equals(type) && !ownedSkins.contains(s)){
                availableSkins.add(s);
            }
        }

        int randomIndex = (int) (Math.random() * availableSkins.size());
        Skin skin = availableSkins.get(randomIndex);
        getSkinInventoryByUsername(username).addSkinToList(skin);
        userRepository.save(userRepository.findUserByUsername(username));
        return skin;
    }

    @Override
    public void updateSkinInventoryByUsername(String username, SkinInventory skinInventory) {
        User user = userRepository.findUserByUsername(username);
        user.setSkinInventory(skinInventory);
        userRepository.save(user);
    }

    @Override
    public void addBalanceByUsername(String username, int amount) {
        User user = userRepository.findUserByUsername(username);
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }
}
