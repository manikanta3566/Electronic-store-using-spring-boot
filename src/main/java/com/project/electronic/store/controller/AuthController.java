package com.project.electronic.store.controller;

import com.project.electronic.store.dto.GenericResponse;
import com.project.electronic.store.dto.JwtRequest;
import com.project.electronic.store.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0/api/auth")
@Api(value = "AuthController",description = "Auth  Controller",tags = "Auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation(value = "user login portal",tags = "Auth")
    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody JwtRequest jwtRequest){
        return new ResponseEntity<>(new GenericResponse<>(authenticationService.authenticateUser(jwtRequest)), HttpStatus.OK);
    }
}
