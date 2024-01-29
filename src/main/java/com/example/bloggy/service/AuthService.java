package com.example.bloggy.service;

import com.example.bloggy.dto.auth.LoginRequest;
import com.example.bloggy.dto.auth.RegisterRequest;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.EmailAlreadyExistsException;
import com.example.bloggy.exception.EmailNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    UserResponse login(LoginRequest loginRequest) throws EmailNotFoundException;
    UserResponse register(RegisterRequest registerRequest) throws EmailNotFoundException, EmailAlreadyExistsException;

}
