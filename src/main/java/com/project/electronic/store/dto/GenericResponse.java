package com.project.electronic.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenericResponse<T> {
    private T data;
    private int statusCode;

    private long timestamp;

    public GenericResponse(T data){
        this.data=data;
        statusCode= HttpStatus.OK.value();
        timestamp=System.currentTimeMillis();
    }

    public GenericResponse(T data,int statusCode){
        this.data=data;
        this.statusCode= statusCode;
        timestamp=System.currentTimeMillis();
    }

}
