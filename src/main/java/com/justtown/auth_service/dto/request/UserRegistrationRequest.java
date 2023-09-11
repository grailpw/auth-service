package com.justtown.auth_service.dto.request;


import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String passwordConfirm;
    private String email;
}
