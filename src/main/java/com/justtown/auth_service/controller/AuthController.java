package com.justtown.auth_service.controller;

import com.justtown.auth_service.dto.request.*;
import com.justtown.auth_service.dto.response.JwtResponse;
import com.justtown.auth_service.service.AuthService;
import com.justtown.auth_service.service.UserService;
import com.justtown.auth_service.util.JwtTokenUtils;
import com.justtown.auth_service.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest request) {
        return authService.createAuthToken(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody UserRegistrationRequest request) {
        return authService.registerNewUser(request);
    }

}
