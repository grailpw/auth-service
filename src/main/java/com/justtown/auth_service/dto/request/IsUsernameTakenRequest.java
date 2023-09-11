package com.justtown.auth_service.dto.request;

import lombok.Data;

@Data
public class IsUsernameTakenRequest {
    private String username;
}
