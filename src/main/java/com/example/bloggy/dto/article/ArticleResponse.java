package com.example.bloggy.dto.article;

import com.example.bloggy.dto.comment.CommentResponse;
import com.example.bloggy.dto.user.UserDTO;
import com.example.bloggy.dto.user.UserResponse;
import com.example.bloggy.model.Media;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleResponse {

    private String id;
    @NotEmpty(message = "This field is required")
    private String title;
    @NotEmpty(message = "This field is required")
    private String content;
    @NotNull(message = "This field is required")
    private LocalDateTime createdAt;
    @NotEmpty(message = "This field is required")
    private List<String> tags;
    @NotNull(message = "This field is required")
    private UserResponse author;
    private List<CommentResponse> comments;
    private List<Media> medias;

}
