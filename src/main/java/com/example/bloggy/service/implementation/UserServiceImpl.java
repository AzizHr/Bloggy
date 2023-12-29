package com.example.bloggy.service.implementation;

import com.example.bloggy.dto.user.UserDTO;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.model.User;
import com.example.bloggy.repository.UserRepository;
import com.example.bloggy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse save(UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
        user.setCreatedAt(LocalDateTime.now());
        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public UserResponse update(UserDTO userDTO) throws NotFoundException {
        if(userRepository.findById(userDTO.getId()).isPresent()) {
            User user = modelMapper.map(userDTO, User.class);

            return modelMapper.map(userRepository.save(user), UserResponse.class);
        }

        throw new NotFoundException("No user was found with ID of "+userDTO.getId());
    }

    @Override
    public UserResponse findById(String id) throws NotFoundException {
        return modelMapper
                .map(userRepository
                                .findById(id)
                                .orElseThrow(() -> new NotFoundException("No user was found with ID of "+id)),
                        UserResponse.class);
    }

    @Override
    public Void delete(String id) throws NotFoundException {
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
        throw new NotFoundException("No user was found with ID of "+id);
    }

    @Override
    public List<UserResponse> findAll() {
        return List.of(modelMapper.map(userRepository.findAll(),
                UserResponse[].class));
    }
}
