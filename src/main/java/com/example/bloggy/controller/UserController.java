package com.example.bloggy.controller;

import com.example.bloggy.dto.user.UserDTO;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.exception.NotFoundException;
import com.example.bloggy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserDTO userDTO) throws NotFoundException {
        return new ResponseEntity<>(userService.save(userDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponse> edit(@RequestBody UserDTO userDTO) throws NotFoundException {
        return new ResponseEntity<>(userService.update(userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable String id) throws NotFoundException {
        userService.delete(id);
        return new ResponseEntity<>(Map.of("message", "User was removed with success"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> medias() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> media(@PathVariable String id) throws NotFoundException {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

}
