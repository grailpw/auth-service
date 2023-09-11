package com.justtown.auth_service.service;

import com.justtown.auth_service.dto.request.JwtRequest;
import com.justtown.auth_service.dto.request.UpdatePasswordRequest;
import com.justtown.auth_service.dto.request.UserRegistrationRequest;
import com.justtown.auth_service.dto.response.JwtResponse;
import com.justtown.auth_service.entity.User;
import com.justtown.auth_service.util.JwtTokenUtils;
import com.justtown.auth_service.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final ResponseUtils responseUtils;


    public ResponseEntity<?> createAuthToken(JwtRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return responseUtils.wrongPassword();
        }

        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = tokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    public ResponseEntity<?> registerNewUser(UserRegistrationRequest request) {
        Optional<ResponseEntity<?>> optional = validateUserRegistration(request);
        if (optional.isPresent()) {
            return optional.get();
        }
        User user = userService.createNewUser(request);
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String token = tokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> updatePassword(UpdatePasswordRequest request) {
        return userService.updatePassword(request);
    }

    private Optional<ResponseEntity<?>> validateUserRegistration(UserRegistrationRequest request) {
        if (userService.isEmailTaken(request.getEmail())) {
            return Optional.of(responseUtils.emailTaken());
        }
        if (userService.isUsernameTaken(request.getUsername())) {
            return Optional.of(responseUtils.usernameTaken());
        }
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            return Optional.of(responseUtils.passwordMissMatch());
        }
        return Optional.empty();
    }
}