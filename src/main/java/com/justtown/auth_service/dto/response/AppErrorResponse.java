package com.justtown.auth_service.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class AppErrorResponse {
    private int status;
    private String message;
    private Date timestamp;

    public AppErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
