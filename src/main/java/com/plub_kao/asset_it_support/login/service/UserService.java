package com.plub_kao.asset_it_support.login.service;


import com.plub_kao.asset_it_support.login.dto.CreateUserRequest;
import com.plub_kao.asset_it_support.login.dto.UpdateUserRequest;
import com.plub_kao.asset_it_support.login.entity.User;
import com.plub_kao.asset_it_support.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Map<String, Object> getAll() {
        return Map.of("success", true, "users", repo.findAll());
    }

    public Map<String, Object> create(CreateUserRequest req) {

        if (repo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        String role = req.getRole() == null ? "USER" : req.getRole().toUpperCase();
        user.setRole("ROLE_" + role);

        repo.save(user);

        return Map.of("success", true, "message", "User created successfully", "user", user);
    }

    public Map<String, Object> update(Long id, UpdateUserRequest req) {

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getEmail() != null) user.setEmail(req.getEmail());
        if (req.getPassword() != null) user.setPassword(encoder.encode(req.getPassword()));
        if (req.getRole() != null) {
            String normalized = req.getRole().toUpperCase();
            if (!normalized.startsWith("ROLE_")) {
                normalized = "ROLE_" + normalized;
            }
            user.setRole(normalized);
        }

        repo.save(user);

        return Map.of("success", true, "message", "User updated", "user", user);
    }

    public Map<String, Object> delete(Long id) {

        if (!repo.existsById(id)) throw new RuntimeException("User not found");

        repo.deleteById(id);

        return Map.of("success", true, "message", "User deleted successfully");
    }


}
