package com.project.electronic.store.service;

import com.project.electronic.store.dto.UserDto;
import com.project.electronic.store.util.ListingResponse;

import java.util.List;

public interface UserService {

   UserDto createUser(UserDto userDto);

   UserDto getUserById(String id);

   ListingResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

   UserDto updateUser(UserDto userDto,String id);

   void deleteUser(String id);

   List<UserDto> searchUser(String keyword);


}
