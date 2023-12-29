package com.example.bloggy.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User {

    @Id
    private String id;
    @NotEmpty(message = "This field is required")
    private String firstname;
    @NotEmpty(message = "This field is required")
    private String lastname;
    @NotEmpty(message = "This field is required")
    private String email;
    @NotEmpty(message = "This field is required")
    private String password;
    @NotNull(message = "This field is required")
    private LocalDateTime createdAt;
    private List<Article> articles;

}
