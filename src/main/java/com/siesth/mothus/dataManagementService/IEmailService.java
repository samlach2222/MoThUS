package com.siesth.mothus.dataManagementService;

public interface IEmailService {

    public void sendEmail(String to, String subject, String body);
}
