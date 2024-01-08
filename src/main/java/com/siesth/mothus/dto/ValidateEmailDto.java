package com.siesth.mothus.dto;

public class ValidateEmailDto {
    private String code;
    private String username;

    public ValidateEmailDto() {}

    public ValidateEmailDto(String code, String username) {
        this.code = code;
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
