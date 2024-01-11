package com.siesth.mothus.dataManagementService;

public interface IEmailService {

    public void sendEmail(String to, String subject, String body);

    int getValidationCodeTime(String username);

    boolean checkValidationCode(String username, String code);

    String getValidationCode(String username);

    void createNewValidationCode(String username);

    int getDurationMinutes(String username);

    void removeValidationCode(String username);
}
