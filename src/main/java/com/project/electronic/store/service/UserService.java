package com.project.electronic.store.service;

import com.project.electronic.store.dto.UserDto;

import java.util.List;

public interface UserService {

   UserDto createUser(UserDto userDto);

   UserDto getUserById(String id);

   List<UserDto> getAllUsers();

   UserDto updateUser(UserDto userDto,String id);

   void deleteUser(String id);

   List<UserDto> searchUser(String keyword);


}
