package com.project.electronic.store.service.impl;

import com.project.electronic.store.entity.User;
import com.project.electronic.store.exception.GlobalException;
import com.project.electronic.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).
                orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        return user;
    }
}
