package com.project.electronic.store.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JwtRequest {
    private String username;
    private String password;
}
