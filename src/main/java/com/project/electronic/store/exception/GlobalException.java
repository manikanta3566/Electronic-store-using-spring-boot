package com.project.electronic.store.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class GlobalException extends RuntimeException{
    private int statusCode;

    private HttpStatus httpStatus;
    public GlobalException(String message,int statusCode,HttpStatus httpStatus) {
        super(message);
        this.statusCode=statusCode;
        this.httpStatus=httpStatus;
    }
}
