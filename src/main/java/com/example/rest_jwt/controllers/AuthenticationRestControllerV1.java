package com.example.rest_jwt.controllers;

import com.example.rest_jwt.dto.AuthenticationRequestDto;
import com.example.rest_jwt.models.User;
import com.example.rest_jwt.security.jwt.JwtTokenProvider;
import com.example.rest_jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(@Lazy AuthenticationManager authenticationManager, @Lazy JwtTokenProvider jwtTokenProvider, @Lazy UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null){
                throw new UsernameNotFoundException("User with username: "+ username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>(){
                {
                    put("username", username);
                    put("token", token);
                }
            };

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
