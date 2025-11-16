package com.plub_kao.asset_it_support.login.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String userRole; // "ADMIN" or "STAFF"
}
