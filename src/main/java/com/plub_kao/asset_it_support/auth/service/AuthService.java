package com.plub_kao.asset_it_support.auth.service;


import com.plub_kao.asset_it_support.auth.dto.LoginRequest;
import com.plub_kao.asset_it_support.auth.dto.LoginResponse;
import com.plub_kao.asset_it_support.auth.dto.MeResponse;
import com.plub_kao.asset_it_support.login.dto.UserResponse;
import com.plub_kao.asset_it_support.login.entity.User;
import com.plub_kao.asset_it_support.login.repository.UserRepository;
import com.plub_kao.asset_it_support.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return LoginResponse.builder()
                .success(true)
                .token(token)
                .user(UserResponse.fromEntity(user))
                .build();
    }

    public MeResponse me(String email) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return MeResponse.builder()
                .success(true)
                .user(UserResponse.fromEntity(user))
                .build();
    }

    public Map<String, Object> logout() {
        return Map.of(
                "success", true,
                "message", "Logged out successfully"
        );
    }

}
