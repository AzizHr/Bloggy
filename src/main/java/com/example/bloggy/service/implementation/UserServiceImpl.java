package com.example.bloggy.service.implementation;

import com.example.bloggy.exception.EmailNotFoundException;
import com.example.bloggy.model.User;
import com.example.bloggy.repository.UserRepository;
import com.example.bloggy.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByEmail(String email) throws EmailNotFoundException {
        if(userRepository.findUserByEmail(email).isPresent()) {
            return userRepository.findUserByEmail(email).get();
        }
        throw new EmailNotFoundException("No user exists with provided email");
    }
}
