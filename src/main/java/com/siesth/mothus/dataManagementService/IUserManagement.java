package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.dto.RegistrationDto;
import com.siesth.mothus.model.ValidationCode;

public interface IUserManagement {

    /**
     * Creates a new user
     * @param registrationDto the registration data
     * @return true if the user was created, false otherwise
     */
    public boolean createNewUser(RegistrationDto registrationDto);

    /**
     * Checks if the login is valid
     * @param registrationDto the registration data
     * @return true if the login is valid, false otherwise
     */
    public boolean checkLogin(RegistrationDto registrationDto);

    void getUserByUsernameAndUpdateValidationCode(String username, ValidationCode validationCode);
}
