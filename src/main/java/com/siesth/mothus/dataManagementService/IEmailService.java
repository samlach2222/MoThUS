package com.siesth.mothus.dataManagementService;

/**
 * This interface is used to manage the email service.
 */
public interface IEmailService {
    /**
     * This method is used to send an email.
     * @param to the receiver
     * @param subject the subject
     * @param body the body
     */
    void sendEmail(String to, String subject, String body);

    /**
     * This method is used to get the validation code time.
     * @param username the username to get the validation code time
     * @return the validation code time
     */
    int getValidationCodeTime(String username);

    /**
     * This method is used to check if the validation code is valid.
     * @param username the username to check the validation code
     * @param code the code to check
     * @return true if the validation code is valid, false otherwise
     */
    boolean checkValidationCode(String username, String code);

    /**
     * This method is used to get the validation code.
     * @param username the username to get the validation code
     * @return the validation code
     */
    int getValidationCode(String username);

    /**
     * This method is used to create a new validation code.
     * @param username the username to create a new validation code
     */
    void createNewValidationCode(String username);
}
