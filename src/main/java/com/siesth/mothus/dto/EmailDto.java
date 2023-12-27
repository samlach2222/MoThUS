package com.siesth.mothus.dto;

public class EmailDto {
    private String to;       // Recipient's email address
    private String subject;  // Email subject
    private String body;     // Email body

    // Constructors, getters, and setters
    public EmailDto() {
    }

    public EmailDto(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
