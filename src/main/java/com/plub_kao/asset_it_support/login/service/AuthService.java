package com.plub_kao.asset_it_support.login.service;


import com.plub_kao.asset_it_support.login.dto.LoginRequest;
import com.plub_kao.asset_it_support.login.dto.LoginResponse;
import com.plub_kao.asset_it_support.login.dto.RegisterRequest;
import com.plub_kao.asset_it_support.login.dto.RegisterResponse;
import com.plub_kao.asset_it_support.login.entity.User;
import com.plub_kao.asset_it_support.login.repository.UserRepository;
import com.plub_kao.asset_it_support.login.security.JwtUtil;
import com.plub_kao.asset_it_support.login.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username นี้มีอยู่แล้ว");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(request.getUserRole().toUpperCase());
        userRepository.save(user);

        return new RegisterResponse("สมัครสมาชิกสำเร็จ");
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("รหัสผ่านไม่ถูกต้อง");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(gr -> gr.getAuthority().replace("ROLE_", ""))
                .orElse("");

        return new LoginResponse("เข้าสู่ระบบสำเร็จ", role, token);
    }
    
}
