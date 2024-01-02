package com.example.bloggy.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {

    private String id;
    @NotEmpty(message = "Firstname is required")
    private String firstname;
    @NotEmpty(message = "Lastname is required")
    private String lastname;
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;

}
