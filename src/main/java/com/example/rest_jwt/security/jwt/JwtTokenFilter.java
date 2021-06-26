package com.example.rest_jwt.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// to filter request in available token(фильтровать запросы на наличие токена)
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // to validate every request(has token - continue to work, no token - good bay)
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        if (token != null && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            if (authentication != null){
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
