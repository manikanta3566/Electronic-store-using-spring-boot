package com.project.electronic.store.service.impl;

import com.project.electronic.store.dto.JwtRequest;
import com.project.electronic.store.dto.JwtResponse;
import com.project.electronic.store.dto.UserDto;
import com.project.electronic.store.exception.GlobalException;
import com.project.electronic.store.security.JwtUtil;
import com.project.electronic.store.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JwtResponse authenticateUser(JwtRequest jwtRequest) {
        if (Objects.isNull(jwtRequest.getUsername()) || Objects.isNull(jwtRequest.getPassword())) {
            throw new GlobalException("invalid credential", HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED);
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException exception) {
            throw new GlobalException("invalid credential", HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return new JwtResponse(token, modelMapper.map(userDetails, UserDto.class));
    }
}
