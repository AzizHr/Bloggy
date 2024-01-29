package com.example.bloggy.service;

import com.example.bloggy.exception.EmailNotFoundException;
import com.example.bloggy.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findUserByEmail(String email) throws EmailNotFoundException;

}
