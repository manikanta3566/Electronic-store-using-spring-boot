package com.project.electronic.store.controller;

import com.project.electronic.store.dto.GenericResponse;
import com.project.electronic.store.dto.ListingResponse;
import com.project.electronic.store.dto.UserDto;
import com.project.electronic.store.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v0/api/users")
@Api(value = "UserController",description = "User Controller",tags = "User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<GenericResponse> createUser(@Valid  @RequestBody UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(new GenericResponse<>(user), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<GenericResponse> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue ="0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue ="5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue ="createdDate",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue ="desc",required = false) String sortDir

    ) {
        ListingResponse<UserDto> users = userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(new GenericResponse<>(users), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<GenericResponse> getUserById(@PathVariable("userId") String id) {
        UserDto user = userService.getUserById(id);
        return new ResponseEntity<>(new GenericResponse<>(user), HttpStatus.OK);
    }

    @PutMapping("{userId}")
    public ResponseEntity<GenericResponse> updateUser(@PathVariable("userId") String id, @Valid @RequestBody UserDto userDto) {
        UserDto user = userService.updateUser(userDto, id);
        return new ResponseEntity<>(new GenericResponse<>(user), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("{userId}")
    public ResponseEntity<GenericResponse> deleteUser(@PathVariable("userId") String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new GenericResponse<>("user deleted successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<GenericResponse> searchUser(@PathVariable("keyword") String keyword) {
        List<UserDto> users = userService.searchUser(keyword);
        return new ResponseEntity<>(new GenericResponse<>(users), HttpStatus.OK);
    }

    @PostMapping("{userId}/images")
    public ResponseEntity<GenericResponse> uploadUserImage(@PathVariable String userId, @RequestParam("file")MultipartFile file){
        return new ResponseEntity<>(new GenericResponse(userService.userImageUpload(file,userId),HttpStatus.CREATED.value()),HttpStatus.CREATED);
    }

    @GetMapping(value = "{userId}/images",produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> serveUserImage(@PathVariable String userId){
      return ResponseEntity
              .ok(userService.getUserImageResources(userId));
    }

}
