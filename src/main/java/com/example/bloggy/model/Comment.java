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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Comment {

    @Id
    private String id;
    @NotEmpty(message = "This field is required")
    private String content;
    @NotNull(message = "This field is required")
    private String articleId;
    @NotEmpty(message = "This field is required")
    private String authorId;
    private LocalDateTime createdAt;

}
