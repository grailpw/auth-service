package com.justtown.auth_service.controller;


import com.justtown.auth_service.dto.request.IsEmailTakenRequest;
import com.justtown.auth_service.dto.request.IsUsernameTakenRequest;
import com.justtown.auth_service.dto.request.UpdatePasswordRequest;
import com.justtown.auth_service.dto.response.IsEmailTakenResponse;
import com.justtown.auth_service.dto.response.IsUsernameTakenResponse;
import com.justtown.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/is-email-taken")
    public ResponseEntity<?> isEmailTaken(@RequestBody IsEmailTakenRequest request) {
        return ResponseEntity.ok(
                new IsEmailTakenResponse(
                        userService.isEmailTaken(request.getEmail())
                )
        );
    }

    @GetMapping("/is-username-taken")
    public ResponseEntity<?> isUsernameTaken(@RequestBody IsUsernameTakenRequest request) {
        return ResponseEntity.ok(
                new IsUsernameTakenResponse(
                        userService.isUsernameTaken(request.getUsername())
                )
        );
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request) {
        return userService.updatePassword(request);
    }

}
