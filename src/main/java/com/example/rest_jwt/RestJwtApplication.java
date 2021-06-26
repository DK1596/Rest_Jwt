package com.example.rest_jwt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;

@SpringBootApplication
public class RestJwtApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(RestJwtApplication.class, args);
    }

}
