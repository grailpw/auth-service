package com.justtown.auth_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IsEmailTakenResponse {
    private boolean isEmailTaken;
}
