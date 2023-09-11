package com.justtown.auth_service.service;

import com.justtown.auth_service.dto.request.IsUsernameTakenRequest;
import com.justtown.auth_service.dto.request.UpdatePasswordRequest;
import com.justtown.auth_service.dto.request.UserRegistrationRequest;
import com.justtown.auth_service.dto.response.IsUsernameTakenResponse;
import com.justtown.auth_service.entity.User;
import com.justtown.auth_service.repository.UserRepository;
import com.justtown.auth_service.util.ResponseUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final ResponseUtils responseUtils;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("user"))
        );
    }

    public Boolean isUsernameTaken(String username) {
        return repository.existsByUsername(username);
    }

    public Boolean isEmailTaken(String email) {
        return repository.existsByEmail(email);
    }

    public User createNewUser(UserRegistrationRequest request) {
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail()
        );
        repository.save(user);
        return user;
    }

    public ResponseEntity<?> updatePassword(UpdatePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = repository.findByUsername(username).orElseThrow();
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            responseUtils.wrongPassword();
        }
        if (!request.getNewPassword().equals(request.getPasswordConfirm())) {
            responseUtils.passwordMissMatch();
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
        return ResponseEntity.ok("Password successfully updated");
    }
}
