package com.project.electronic.store.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtRequest {
    private String username;
    private String password;
}
