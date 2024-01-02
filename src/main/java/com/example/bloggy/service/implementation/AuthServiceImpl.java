package com.example.bloggy.service.implementation;

import com.example.bloggy.dto.auth.AuthResponse;
import com.example.bloggy.dto.auth.LoginRequest;
import com.example.bloggy.dto.auth.RegisterRequest;
import com.example.bloggy.exception.EmailAlreadyExistException;
import com.example.bloggy.model.CustomUser;
import com.example.bloggy.repository.UserRepository;
import com.example.bloggy.service.AuthService;
import com.example.bloggy.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager,
                           ModelMapper modelMapper) {

        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) throws EmailAlreadyExistException {
        CustomUser user = modelMapper.map(registerRequest, CustomUser.class);
        if(userRepository.findByEmail(registerRequest.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setRole("USER");
            user.setRegisteredAt(LocalDateTime.now());
            userRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setToken(jwtToken);
            return authResponse;
        }
        throw new EmailAlreadyExistException("A user already exist with this email");
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        CustomUser user = userRepository.findByEmail(loginRequest.getEmail());
        String jwtToken = jwtService.generateToken(user);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        return authResponse;
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
