package com.project.electronic.store.service;

import com.project.electronic.store.dto.RoleDto;
import com.project.electronic.store.dto.UserDto;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);

    UserDto addRoleToUser(String roleId,String userId);
}
