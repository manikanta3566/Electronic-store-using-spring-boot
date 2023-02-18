package com.project.electronic.store.controller;

import com.project.electronic.store.dto.GenericResponse;
import com.project.electronic.store.dto.RoleDto;
import com.project.electronic.store.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v0/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public ResponseEntity<GenericResponse> createRole(@RequestBody RoleDto roleDto) {
        return new ResponseEntity<>(new GenericResponse<>(roleService.createRole(roleDto), HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @PostMapping("{roleId}/users/{userId}")
    public ResponseEntity<GenericResponse> addRoleToUser(@PathVariable String roleId,
                                                         @PathVariable String userId) {
        return new ResponseEntity<>(new GenericResponse<>(roleService.addRoleToUser(roleId, userId)), HttpStatus.OK);
    }
}
