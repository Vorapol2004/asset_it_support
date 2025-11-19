package com.plub_kao.asset_it_support.auth.dto;


import com.plub_kao.asset_it_support.login.dto.UserResponse;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeResponse {

    private boolean success;
    private UserResponse user;

    
}
