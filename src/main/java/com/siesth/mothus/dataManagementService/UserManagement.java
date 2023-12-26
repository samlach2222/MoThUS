package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.model.SkinInventory;
import com.siesth.mothus.model.Stats;
import com.siesth.mothus.model.User;
import com.siesth.mothus.model.UserLanguage;
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
}
