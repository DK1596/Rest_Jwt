package com.example.rest_jwt.security.jwt;

import com.example.rest_jwt.models.Role;
import com.example.rest_jwt.models.Status;
import com.example.rest_jwt.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// not extends
// in this class to generate JwtUser
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),
                mapToGrantedAuthority(new ArrayList<>(user.getRoles()))
        );
    }

    // to convert roles to GrantedAuthority
    private static List<GrantedAuthority> mapToGrantedAuthority(List<Role> userRoles){
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
