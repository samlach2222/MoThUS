package com.siesth.mothus.dataManagementService;

import com.siesth.mothus.dto.RegistrationDto;

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
}
