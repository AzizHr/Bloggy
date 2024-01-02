package com.example.bloggy.service;

import com.example.bloggy.dto.auth.AuthResponse;
import com.example.bloggy.dto.auth.LoginRequest;
import com.example.bloggy.dto.auth.RegisterRequest;
import com.example.bloggy.exception.EmailAlreadyExistException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    public AuthResponse register(RegisterRequest registerRequest) throws EmailAlreadyExistException;
    public AuthResponse login(LoginRequest loginRequest);
    public void logout();

}
