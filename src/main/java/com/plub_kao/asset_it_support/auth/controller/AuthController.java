package com.plub_kao.asset_it_support.auth.controller;


import com.plub_kao.asset_it_support.auth.dto.LoginRequest;
import com.plub_kao.asset_it_support.auth.security.JwtService;

import com.plub_kao.asset_it_support.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("Authorization") String auth) {
        String token = auth.substring(7);
        String email = jwtService.extractEmail(token);
        return ResponseEntity.ok(authService.me(email));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(authService.logout());
    }
}
