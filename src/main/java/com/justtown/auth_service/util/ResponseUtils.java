package com.justtown.auth_service.util;


import com.justtown.auth_service.dto.response.AppErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtils {

    public ResponseEntity<?> emailTaken() {
        return new ResponseEntity<>(
                new AppErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Email taken"
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<?> usernameTaken() {
        return new ResponseEntity<>(
                new AppErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Username taken"
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<?> passwordMissMatch(){
        return new ResponseEntity<>(
                new AppErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Password and password confirm are not identical"
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<?> wrongPassword() {
        return new ResponseEntity<>(
                new AppErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Wrong username or password"
                ),
                HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> unauthorized() {
        return new ResponseEntity<>(
                new AppErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "You are unauthorized"
                ),
                HttpStatus.UNAUTHORIZED
        );
    }
}
