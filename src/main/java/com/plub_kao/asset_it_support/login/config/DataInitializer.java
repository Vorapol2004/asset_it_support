package com.plub_kao.asset_it_support.login.config;

import com.plub_kao.asset_it_support.login.entity.User;
import com.plub_kao.asset_it_support.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");


            repo.save(admin);

            System.out.println("✅ Default admin created:");
            System.out.println(" email: admin@example.com");
            System.out.println(" password: admin123");
        }
    }
}
