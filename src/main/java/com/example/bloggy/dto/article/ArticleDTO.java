package com.example.bloggy.dto.article;

import com.example.bloggy.dto.comment.CommentResponse;
import com.example.bloggy.model.Media;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDTO {

    private String id;
    @NotEmpty(message = "This field is required")
    private String title;
    @NotEmpty(message = "This field is required")
    private String content;
    @NotEmpty(message = "This field is required")
    private List<String> tags;
    @NotEmpty(message = "This field is required")
    private String author;
    @NotNull(message = "This field is required")
    private List<Media> medias;

}
