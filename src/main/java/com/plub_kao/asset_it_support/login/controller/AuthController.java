package com.plub_kao.asset_it_support.login.controller;


import com.plub_kao.asset_it_support.login.dto.LoginRequest;
import com.plub_kao.asset_it_support.login.dto.RegisterRequest;
import com.plub_kao.asset_it_support.login.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/admin/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminHello() {
        return ResponseEntity.ok("Hello Admin");
    }

    @GetMapping("/staff/hello")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<String> staffHello() {
        return ResponseEntity.ok("Hello Staff");
    }
}
