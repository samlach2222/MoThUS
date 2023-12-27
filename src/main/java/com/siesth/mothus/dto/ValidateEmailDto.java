package com.siesth.mothus.dto;

public class ValidateEmailDto {
    private String code;

    public ValidateEmailDto() {}

    public ValidateEmailDto(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
