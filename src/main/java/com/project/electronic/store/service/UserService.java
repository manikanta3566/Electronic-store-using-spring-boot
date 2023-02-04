package com.project.electronic.store.service;

import com.project.electronic.store.dto.FileResponse;
import com.project.electronic.store.dto.UserDto;
import com.project.electronic.store.dto.ListingResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

   UserDto createUser(UserDto userDto);

   UserDto getUserById(String id);

   ListingResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

   UserDto updateUser(UserDto userDto,String id);

   void deleteUser(String id);

   List<UserDto> searchUser(String keyword);

   FileResponse userImageUpload(MultipartFile file,String id);

   byte[] getUserImageResources(String userId);


}
