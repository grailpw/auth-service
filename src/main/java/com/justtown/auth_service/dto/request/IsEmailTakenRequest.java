package com.justtown.auth_service.dto.request;

import lombok.Data;

@Data
public class IsEmailTakenRequest {
    private String email;
}
