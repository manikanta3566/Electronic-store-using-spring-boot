package com.project.electronic.store.service.impl;

import com.project.electronic.store.dto.UserDto;
import com.project.electronic.store.entity.User;
import com.project.electronic.store.exception.GlobalException;
import com.project.electronic.store.repository.UserRepository;
import com.project.electronic.store.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setUserImagePath(userDto.getUserImagePath());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setUserImagePath(userDto.getUserImagePath());
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GlobalException("user not found", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByUsernameContaining(keyword);
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
