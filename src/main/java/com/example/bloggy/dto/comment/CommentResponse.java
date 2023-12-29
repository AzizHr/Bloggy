package com.example.bloggy.dto.comment;

import com.example.bloggy.dto.user.UserResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponse {

    private String id;
    @NotEmpty(message = "This field is required")
    private String content;
    @NotEmpty(message = "This field is required")
    private String article;
    @NotEmpty(message = "This field is required")
    private UserResponse author;
    @NotEmpty(message = "This field is required")
    private LocalDateTime createdAt;

}
