package com.project.electronic.store.controller;

import com.project.electronic.store.dto.UserDto;
import com.project.electronic.store.helper.GenericResponse;
import com.project.electronic.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v0/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<GenericResponse> createUser(@RequestBody UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(new GenericResponse<>(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(new GenericResponse<>(users), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<GenericResponse> getUserById(@PathVariable("userId") String id) {
        UserDto user = userService.getUserById(id);
        return new ResponseEntity<>(new GenericResponse<>(user), HttpStatus.OK);
    }

    @PutMapping("{userId}")
    public ResponseEntity<GenericResponse> updateUser(@PathVariable("userId") String id, @RequestBody UserDto userDto) {
        UserDto user = userService.updateUser(userDto, id);
        return new ResponseEntity<>(new GenericResponse<>(user), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<GenericResponse> deleteUser(@PathVariable("userId") String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new GenericResponse<>("user deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<GenericResponse> searchUser(@PathVariable("keyword") String keyword) {
        List<UserDto> users = userService.searchUser(keyword);
        return new ResponseEntity<>(new GenericResponse<>(users), HttpStatus.OK);
    }

}
