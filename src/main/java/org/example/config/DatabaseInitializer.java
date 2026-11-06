package org.example.config;

import org.example.entities.Role;
import org.example.entities.User;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseInitializer {

    @Bean
    CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            Role adminRole = roleRepo.findByLibelle("ROLE_ADMIN")
                    .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_ADMIN")));
            Role userRole = roleRepo.findByLibelle("ROLE_USER")
                    .orElseGet(() -> roleRepo.save(new Role(null, "ROLE_USER")));

            if (userRepo.findByLogin("hamza").isEmpty()) {
                userRepo.save(new User(null, "hamza", encoder.encode("admin2026"), true, List.of(adminRole, userRole)));
            }

            if (userRepo.findByLogin("salma").isEmpty()) {
                userRepo.save(new User(null, "salma", encoder.encode("user2026"), true, List.of(userRole)));
            }
        };
    }
}
