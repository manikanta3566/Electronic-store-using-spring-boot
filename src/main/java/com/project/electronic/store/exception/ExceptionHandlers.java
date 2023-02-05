package com.project.electronic.store.exception;

import com.project.electronic.store.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse> globalException(GlobalException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), exception.getStatusCode(), exception.getHttpStatus()), exception.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArguementException(MethodArgumentNotValidException exception){
        ExceptionResponse exceptionResponse=new ExceptionResponse();
        exceptionResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        exceptionResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        List<String> errors = exception.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toList());
        exceptionResponse.setDetails(errors);
        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }
}
