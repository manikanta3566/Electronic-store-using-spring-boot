package com.project.electronic.store.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionResponse {
    private String message;
    private int statusCode;
    private HttpStatus httpStatus;
}
