package com.example.bloggy.service.implementation;

import com.example.bloggy.model.CustomUser;
import com.example.bloggy.repository.UserRepository;
import com.example.bloggy.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CustomUser user = userRepository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("No user found with provided email");
        }

        return user;
    }
}
