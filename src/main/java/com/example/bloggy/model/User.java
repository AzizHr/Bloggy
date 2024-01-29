package com.example.bloggy.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
    private LocalDateTime registeredAt;
}