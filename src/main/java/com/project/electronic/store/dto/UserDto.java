package com.project.electronic.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.electronic.store.validator.UsernameValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String id;

    private boolean active;

    @UsernameValid
    private String username;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;
    private LocalDateTime createdDate;

    private String userImagePath;

    private Set<RoleDto> roles;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
