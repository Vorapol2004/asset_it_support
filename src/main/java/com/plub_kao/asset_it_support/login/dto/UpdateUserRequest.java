package com.plub_kao.asset_it_support.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String email;     // optional
    private String password;  // optional
    private String role;      // optional
}
