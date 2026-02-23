package com.ismailjacoby.bankingapi.dto.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private String status;
    private String message;
    private String timestamp = LocalDateTime.now().toString();

    public ErrorResponse(String status, String message, HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }
}
