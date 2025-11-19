package com.plub_kao.asset_it_support.auth.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutResponse {

    private boolean success;
    private String message;

    public LogoutResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
