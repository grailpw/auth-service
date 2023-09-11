package com.justtown.auth_service.config;

import com.justtown.auth_service.entity.User;
import com.justtown.auth_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        return args -> {
            repository.saveAll(List.of(
                    new User("john", encoder.encode("100"), "john@gmail.com"),
                    new User("jane", encoder.encode("100"), "jane@gmail.com")
            ));
        };
    }
}
