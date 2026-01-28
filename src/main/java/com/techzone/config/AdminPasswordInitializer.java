package com.techzone.config;

import com.techzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminPasswordInitializer implements ApplicationRunner {

    private static final String ADMIN_EMAIL = "admin@techzone.com";
    private static final String ADMIN_PASSWORD = "password123";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        userRepository.findByEmail(ADMIN_EMAIL).ifPresent(user -> {
            if (!passwordEncoder.matches(ADMIN_PASSWORD, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
                userRepository.save(user);
            }
        });
    }
}
