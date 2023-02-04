package com.project.electronic.store.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@NoArgsConstructor
@Data
public class ExceptionResponse {
    private String message;
    private int statusCode;
    private HttpStatus httpStatus;

    private List<String> details;

    public ExceptionResponse(String message, int statusCode, HttpStatus httpStatus) {
        this.message = message;
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }

    public ExceptionResponse(String message, int statusCode, HttpStatus httpStatus, List<String> details) {
        this.message = message;
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.details = details;
    }
}
