package com.example.bloggy.service;

import com.example.bloggy.dto.user.UserDTO;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserService {

    UserResponse save(UserDTO userDTO);
    UserResponse update(UserDTO userDTO) throws NotFoundException;
    UserResponse findById(String id) throws NotFoundException;
    Void delete(String email) throws NotFoundException;
    List<UserResponse> findAll();

}
