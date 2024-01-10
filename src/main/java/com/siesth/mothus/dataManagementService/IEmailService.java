package com.siesth.mothus.dataManagementService;

public interface IEmailService {

    void sendEmail(String to, String subject, String body);

    int getValidationCodeTime(String username);

    boolean checkValidationCode(String username, String code);

    int getValidationCode(String username);

    void createNewValidationCode(String username);
}
