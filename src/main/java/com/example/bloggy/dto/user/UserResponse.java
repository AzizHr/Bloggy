package com.example.bloggy.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String id;
    @NotEmpty(message = "Firstname is required")
    private String firstname;
    @NotEmpty(message = "Lastname is required")
    private String lastname;
    @NotEmpty(message = "Email is required")
    private String email;
    @NotNull(message = "This field is required")
    private LocalDateTime registeredAt;

}
