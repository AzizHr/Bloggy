package com.example.bloggy.service.implementation;

import com.example.bloggy.dto.auth.LoginRequest;
import com.example.bloggy.dto.auth.RegisterRequest;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.EmailAlreadyExistsException;
import com.example.bloggy.exception.EmailNotFoundException;
import com.example.bloggy.model.User;
import com.example.bloggy.repository.UserRepository;
import com.example.bloggy.service.AuthService;
import com.example.bloggy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(UserRepository userRepository, UserService userService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) throws EmailNotFoundException {
        User user = userService.findUserByEmail(loginRequest.getEmail());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return modelMapper.map(user, UserResponse.class);
        } else {
            throw new EmailNotFoundException("Email or password is incorrect");
        }
    }


    @Override
    public UserResponse register(RegisterRequest registerRequest) throws EmailAlreadyExistsException {

        if(userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("This email is already is use");
        }

        User user = modelMapper.map(registerRequest, User.class);
        user.setRegisteredAt(LocalDateTime.now());
        return modelMapper.map(userRepository.save(user), UserResponse.class);

    }
}
