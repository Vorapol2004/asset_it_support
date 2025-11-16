package com.plub_kao.asset_it_support.login.config;


import com.plub_kao.asset_it_support.login.entity.User;
import com.plub_kao.asset_it_support.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner init() {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // เปลี่ยนใน production
                admin.setUserRole("ADMIN");
                userRepository.save(admin);
                System.out.println("Default admin created: admin/admin123");
            }
        };
    }
}
