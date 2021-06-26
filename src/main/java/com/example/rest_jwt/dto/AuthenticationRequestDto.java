package com.example.rest_jwt.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
