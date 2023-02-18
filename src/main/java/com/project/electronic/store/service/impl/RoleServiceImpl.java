package com.project.electronic.store.service.impl;

import com.project.electronic.store.dto.RoleDto;
import com.project.electronic.store.dto.UserDto;
import com.project.electronic.store.entity.Role;
import com.project.electronic.store.entity.User;
import com.project.electronic.store.exception.GlobalException;
import com.project.electronic.store.repository.RoleRepository;
import com.project.electronic.store.repository.UserRepository;
import com.project.electronic.store.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        Role savedRole = roleRepository.save(role);
        return modelMapper.map(savedRole, RoleDto.class);
    }

    @Override
    public UserDto addRoleToUser(String roleId, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        Set<Role> roles=new HashSet<>();
        roles.add(role);
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }
}
