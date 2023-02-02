package com.project.electronic.store.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlers {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse> globalException(GlobalException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), exception.getStatusCode(), exception.getHttpStatus()), exception.getHttpStatus());
    }
}
