package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.model.*;
import com.siesth.mothus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManagement implements IUserManagement {
    @Autowired
    UserRepository userRepository;

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

    @Override
    public boolean checkLogin(RegistrationDto registrationDto) {
        User user = userRepository.findUserByUsername(registrationDto.getUsername());
        if (user == null) {
            return false;
        }
        Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
        return arg2SpringSecurity.matches(registrationDto.getPassword(), user.getPassword());
    }

    @Override
    public void getUserByUsernameAndUpdateValidationCode(String username, ValidationCode validationCode) {
        User user = userRepository.findUserByUsername(username);
        user.setValidationCode(validationCode);
        userRepository.save(user);
    }

    @Override
    public String getEmailByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getMail();
    }

    @Override
    public String getLanguageByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user.getCurrentLanguage().toString();
    }

    @Override
    public void updateLanguageByUsername(String username, String language) {
        User user = userRepository.findUserByUsername(username);
        user.setCurrentLanguage(UserLanguage.valueOf(language));
        userRepository.save(user);
    }

    @Override
    public void updateUsernameByUsername(String oldUsername, String newUsername) {
        User user = userRepository.findUserByUsername(oldUsername);
        user.setUsername(newUsername);
        userRepository.save(user);
    }

    @Override
    public void updatePasswordByUsername(String username, String password) {
        User user = userRepository.findUserByUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }
}
