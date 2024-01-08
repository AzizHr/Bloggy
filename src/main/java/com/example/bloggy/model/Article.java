package com.example.bloggy.model;

import jakarta.validation.constraints.NotEmpty;
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
public class Article {

    @Id
    private String id;
    @NotEmpty(message = "This field is required")
    private String title;
    @NotEmpty(message = "This field is required")
    private String content;
    @NotEmpty(message = "This field is required")
    private LocalDateTime createdAt;
    @NotEmpty(message = "This field is required")
    private List<String> tags;
    @NotEmpty(message = "This field is required")
    private String authorId;
    private List<Comment> comments;
    private List<Media> medias;

}
