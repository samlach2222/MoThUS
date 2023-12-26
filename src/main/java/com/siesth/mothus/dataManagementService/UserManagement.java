package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.model.SkinInventory;
import com.siesth.mothus.model.Stats;
import com.siesth.mothus.model.User;
import com.siesth.mothus.model.UserLanguage;
import com.siesth.mothus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (userRepository.existsUserByUsernameAndPassword(registrationDto.getUsername(), registrationDto.getPassword())) {
            return true;
        }
        else {
            return false;
        }
    }
}
