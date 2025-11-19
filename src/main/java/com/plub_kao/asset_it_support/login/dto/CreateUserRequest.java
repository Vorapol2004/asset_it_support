package com.plub_kao.asset_it_support.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    private String email;
    private String password;
    private String role = "user";
}
