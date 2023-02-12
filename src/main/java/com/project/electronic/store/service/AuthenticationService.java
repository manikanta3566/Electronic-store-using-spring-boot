package com.project.electronic.store.service;

import com.project.electronic.store.dto.JwtRequest;
import com.project.electronic.store.dto.JwtResponse;

public interface AuthenticationService {
    JwtResponse authenticateUser(JwtRequest jwtRequest);
}
