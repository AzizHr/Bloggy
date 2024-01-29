package com.example.bloggy.controller;

import com.example.bloggy.dto.auth.LoginRequest;
import com.example.bloggy.dto.auth.RegisterRequest;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.EmailAlreadyExistsException;
import com.example.bloggy.exception.EmailNotFoundException;
import com.example.bloggy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) throws EmailNotFoundException {

        return new ResponseEntity<>(authService.login(loginRequest), HttpStatusCode.valueOf(200));

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) throws EmailNotFoundException, EmailAlreadyExistsException {

        return new ResponseEntity<>(authService.register(registerRequest), HttpStatusCode.valueOf(200));

    }

}
